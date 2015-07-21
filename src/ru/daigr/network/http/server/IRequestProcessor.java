package ru.daigr.network.http.server;


@FunctionalInterface
public interface IRequestProcessor {
	public HTTPResponse processRequest(HTTPRequest aRequest);
}
