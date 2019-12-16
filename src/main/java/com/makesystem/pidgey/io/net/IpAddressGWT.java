/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io.net;

import com.makesystem.pidgey.interfaces.AsyncCallback;
import com.makesystem.pidgey.lang.AsyncResponse;
import java.io.IOException;

/**
 *
 * @author riche
 */
public class IpAddressGWT extends IpAddress {

    private static final long serialVersionUID = 8857096786088548802L;

    public static final String JQUERY_API = "ajax.googleapis.com/ajax/libs/jquery";
    public static final String JQUERY_API_URL = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js";

    /**
     * For security and privacy reasons current browsers do not allow access to
     * the local ip address.
     *
     * @return
     * @throws IOException
     */
    @Override
    public String getLocal() throws IOException {
        // For security and privacy reasons current browsers do not allow access to
        // the local ip address.
        return NO_IP;

//        final AsyncResponse<String> response = new AsyncResponse<>();
//        localIp(new AsyncCallback<String>() {
//            @Override
//            public void onSuccess(final String ip) {
//                response.setSuccess(ip);
//            }
//            @Override
//            public void onFailure(final Throwable caught) {
//                response.setFailure(caught);
//            }
//        });
//        if (response.getFailure() != null) {
//            throw new IOException(response.getFailure());
//        }
//        return response.getSuccess();
    }

    /**
     * For security and privacy reasons current browsers do not allow access to
     * the local ip address.
     * 
     * @param asyncCallback 
     */
    @Override
    public void getLocal(final AsyncCallback<String> asyncCallback) {
        // For security and privacy reasons current browsers do not allow access to
        // the local ip address.
        asyncCallback.onSuccess(NO_IP);
//        try {
//            localIp(asyncCallback);
//        } catch (final Throwable throwable) {
//            asyncCallback.onFailure(throwable);
//        }
    }

    native void localIp(final AsyncCallback<String> callback)/*-{

	try {

		//compatibility for firefox and chrome
		var chrome = navigator.userAgent.indexOf('Chrome') > -1;
		var firefox = navigator.userAgent.indexOf('Firefox') > -1;

		if (!chrome && !firefox) {
			callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onSuccess(Ljava/lang/Object;)('0.0.0.0');
		} else {

                    window.RTCPeerConnection = window.RTCPeerConnection
			|| window.mozRTCPeerConnection
			|| window.webkitRTCPeerConnection; //compatibility for firefox and chrome

                    if (!window.RTCPeerConnection) {
			callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onSuccess(Ljava/lang/Object;)('0.0.0.0');
			return;
                    }

                    var pc = new RTCPeerConnection({iceServers : []});
                    var noop = function() {};
				
                    pc.createDataChannel(""); //create a bogus data channel
                    pc.createOffer(pc.setLocalDescription.bind(pc), noop); // create offer and set local description
                    pc.onicecandidate = function(ice) { //listen for candidate events
                    
                        if (!ice || !ice.candidate || !ice.candidate.candidate) {
                            callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onSuccess(Ljava/lang/Object;)('0.0.0.0');
                        } else {
						
                            var candidate = ice.candidate.candidate;
                            var matched = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/.exec(candidate);
								
                            if (!matched || matched.length < 2) {
                                callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onSuccess(Ljava/lang/Object;)('0.0.0.0');
                            } else {
				var ip = matched[1];
				callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onSuccess(Ljava/lang/Object;)(ip);
                            }
						
                            pc.onicecandidate = noop;
			}
                    };
		}

        } catch (throwable) {
            var ioException = @java.io.IOException::new(Ljava/lang/String;)(throwable.message);
            callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ioException);
        }

    }-*/;

    /**
     * 
     * @return
     * @throws IOException 
     */
    @Override
    public String getPublic() throws IOException {
        final AsyncResponse<String> response = new AsyncResponse<>();
        publicIp(false, new AsyncCallback<String>() {
            @Override
            public void onSuccess(final String ip) {
                response.setSuccess(ip);
            }

            @Override
            public void onFailure(final Throwable caught) {
                response.setFailure(caught);
            }
        });

        if (response.getFailure() != null) {
            throw new IOException(response.getFailure());
        }

        return response.getSuccess();
    }

    /**
     * 
     * @param asyncCallback 
     */
    @Override
    public void getPublic(final AsyncCallback<String> asyncCallback) {
        try {
            publicIp(true, asyncCallback);
        } catch (final Throwable throwable) {
            asyncCallback.onFailure(throwable);
        }
    }

    void publicIp(final boolean async, final AsyncCallback<String> callback) {

        final boolean hasApi = hasScript(JQUERY_API);
        if (!hasApi) {
            injectScript(JQUERY_API_URL);
        }

        publicIp(getGetIpURL(), async, callback);

        if (!hasApi) {
            removeScript(JQUERY_API_URL);
        }
    }

    native boolean hasScript(final String src)/*-{
	return document.querySelector('script[src*="' + src + '"]') ? true : false;
    }-*/;

    native void injectScript(final String src)/*-{
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = src;
	head.appendChild(script);
    }-*/;

    native void removeScript(final String src)/*-{
	var script = document.querySelector('script[src*="' + src + '"]');
        if (script)
            script.remove();
    }-*/;

    native void publicIp(
            final String publicIpSource,
            final boolean async,
            final AsyncCallback<String> callback)/*-{
        try {    
            $wnd.jQuery.ajax({
                type : 'GET',
                dataType : 'text',
                url : publicIpSource,
                async : async,
                success : function(ip) {       
                    callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onSuccess(Ljava/lang/Object;)(ip);
                }
            });
        } catch(throwable){
            var ioException = @java.io.IOException::new(Ljava/lang/String;)(throwable.message);
            callback.@com.makesystem.pidgey.interfaces.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ioException);
        }     
    }-*/;

}
