package ru.daigr.network.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Logger;
 
public class HTTPServer implements Runnable {
 
    private Charset charset = Charset.forName("UTF-8");
    private CharsetEncoder encoder = charset.newEncoder();
    private Selector selector = Selector.open();
    private ServerSocketChannel server = ServerSocketChannel.open();
    private boolean isRunning = true;
    private boolean debug = true;
    
    private Logger logger = null;
    
    private IRequestProcessor requestProcessor = null;
 
    /**
     * Create a new server and immediately binds it.
     *
     * @param address the address to bind on
     * @throws IOException if there are any errors creating the server.
     */
    public HTTPServer(InetSocketAddress address, Logger aLogger) throws IOException {
    	
        logger = aLogger;
    	
        logger.info("Init server on address " + address.getHostString());
        server.socket().bind(address);
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);        
        
        resetRequestProcessor();
    }
    
    public void setRequestProcessor(IRequestProcessor aRequestProcessor){
    	logger.info("Setting request processor: " + aRequestProcessor.getClass().getName());
    	this.requestProcessor = aRequestProcessor;
    }
    
    public void resetRequestProcessor() {
    	logger.info("reset request processor, now it's not implemented");
    	requestProcessor = (request) -> {
        	HTTPResponse response = new HTTPResponse();
            response.setContent("Request pricessor not implemented".getBytes());
            return response;
        };
    }
 
    /**
     * Core run method. This is not a thread safe method, however it is non
     * blocking. If an exception is encountered it will be thrown wrapped in a
     * RuntimeException, and the server will automatically be {@link #shutDown}
     */
    @Override
    public final void run() {
        if (isRunning) {
            try {
                selector.selectNow();
                Iterator<SelectionKey> i = selector.selectedKeys().iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    i.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    try {
                        // get a new connection
                        if (key.isAcceptable()) {
                            // accept them
                            SocketChannel client = server.accept();
                            // non blocking please                            
                            client.configureBlocking(false);
                            // show out intentions
                            client.register(selector, SelectionKey.OP_READ);
                            // read from the connection
                        } else if (key.isReadable()) {
                            //  get the client
                            SocketChannel client = (SocketChannel) key.channel();
                            // get the session
                            HTTPSession session = (HTTPSession) key.attachment();
                            // create it if it doesnt exist
                            if (session == null) {
                                session = new HTTPSession(client);
                                key.attach(session);
                            }
                            // get more data
                            session.readData();
                            // decode the message            
                            logger.info("Process request: " + session.readLines.toString());
                            HTTPRequest request = new HTTPRequest(session.readLines.toString());
                            HTTPResponse r = handle(session, request);
                            logger.info("Make response. The content is: " + r.getContent());
                            session.sendResponse(r);
                            session.close();
                        }
                    } catch (Exception ex) {
                        logger.error("Error handling client: " + key.channel());
                        logger.error(ex);
                        if (debug) {
                            ex.printStackTrace();
                        } else {
                            System.err.println(ex);
                            System.err.println("\tat " + ex.getStackTrace()[0]);
                        }
                        if (key.attachment() instanceof HTTPSession) {
                            ((HTTPSession) key.attachment()).close();
                        }
                    }
                }
            } catch (IOException ex) {
                // call it quits
            	logger.error(ex);
            	logger.info("Shutdown server");
                shutdown();
                // throw it as a runtime exception so that Bukkit can handle it
                throw new RuntimeException(ex);
            }
        }
    }
 
    /**
     * Handle a web request.
     *
     * @param session the entire http session
     * @return the handled request
     */
    protected HTTPResponse handle(HTTPSession session, HTTPRequest request) throws IOException {
        return requestProcessor.processRequest(request);
    }
 
    /**
     * Shutdown this server, preventing it from handling any more requests.
     */
    public final void shutdown() {
        isRunning = false;
        try {
            selector.close();
            server.close();
        } catch (IOException ex) {
            // do nothing, its game over
        }
    }
 
    public final class HTTPSession {
 
        private final SocketChannel channel;
        private final ByteBuffer buffer = ByteBuffer.allocate(2048);
        private final StringBuilder readLines = new StringBuilder();        
 
        public HTTPSession(SocketChannel channel) {
            this.channel = channel;            
        }            
 
        /**
         * Get more data from the stream.
         */
        public void readData() throws IOException {
            buffer.limit(buffer.capacity());
            int read = channel.read(buffer);           
            if (read == -1) {
                throw new IOException("End of stream");
            }
                    
            readLines.append(new String(buffer.array()));
        }                
 
        private void writeLine(String line) throws IOException {
            channel.write(encoder.encode(CharBuffer.wrap(line + "\r\n")));
        }
 
        public void sendResponse(HTTPResponse response) {
            response.addDefaultHeaders();
            try {
                writeLine(response.getVersion() + " " + response.getResponseCode() + " " + response.getResponseReason());
                for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                    writeLine(header.getKey() + ": " + header.getValue());
                }
                writeLine("");
                if (response.getContent() != null){
                	channel.write(ByteBuffer.wrap(response.getContent()));
                }                
            } catch (IOException ex) {
                // slow silently
            }
        }
 
        public void close() {
            try {
                channel.close();
            } catch (IOException ex) {
            }
        }
    }            
}
