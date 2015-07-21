package ru.daigr.network.http.server;


@FunctionalInterface
public interface IResponseProcessor {
	public HTTPResponse buildResponse(HTTPRequest aRequest);
}
