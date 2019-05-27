/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.io;

/**
 *
 * @author Richeli.vargas
 */
public class InnetAddressHelperGWT {

    public static void getLocalIp(final GetIpHandler handler){
        try {
            _getLocalIp(handler);
        } catch (final Throwable throwable){
            handler.onFailure(throwable);
        }
    }
            
    private static native void _getLocalIp(final GetIpHandler handler) /*-{	
        window.RTCPeerConnection = window.RTCPeerConnection
            || window.mozRTCPeerConnection
            || window.webkitRTCPeerConnection; //compatibility for firefox and chrome
            var pc = new RTCPeerConnection({
                iceServers : [] 
            }), noop = function() {};
            
            pc.createDataChannel(""); //create a bogus data channel
            pc.createOffer(pc.setLocalDescription.bind(pc), noop); // create offer and set local description
            pc.onicecandidate = function(ice) { //listen for candidate events
            
            if (!ice || !ice.candidate || !ice.candidate.candidate){
                handler.@com.makesystem.pidgey.io.GetIpHandler::onSuccess(Ljava/lang/String;)("0.0.0.0");
            } else {		
                var candidate = ice.candidate.candidate;
                if(!candidate) {
                    handler.@com.makesystem.pidgey.io.GetIpHandler::onSuccess(Ljava/lang/String;)("0.0.0.0");
                    return;
                }		
		
                var matched =  /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/.exec(candidate);		
                if(!matched) {
                    handler.@com.makesystem.pidgey.io.GetIpHandler::onSuccess(Ljava/lang/String;)("0.0.0.0");
                    return;
                }
            
                var local_ip = matched[1];
                handler.@com.makesystem.pidgey.io.GetIpHandler::onSuccess(Ljava/lang/String;)(local_ip);		
                pc.onicecandidate = noop;
            }
	};
    }-*/;

    public static void getPublicIp(final GetIpHandler handler){
        try {
            _getPublicIp(handler);
        } catch (final Throwable throwable){
            handler.onFailure(throwable);
        }
    }
    
    private static native void _getPublicIp(final GetIpHandler handler)/*-{
        function getIp(callback) {
            function response(s) {
		callback(window.userip);

		s.onload = s.onerror = null;
		document.body.removeChild(s);
            }

            function trigger() {
		window.userip = false;

		var s = document.createElement("script");
		s.async = true;
		s.onload = function() {
                    response(s);
		};
		s.onerror = function() {
                    response(s);
		};

		s.src = "https://l2.io/ip.js?var=userip";
		document.body.appendChild(s);
            }

            if (/^(interactive|complete)$/i.test(document.readyState)) {
                trigger();
            } else {
		document.addEventListener('DOMContentLoaded', trigger);
            }
	}

        getIp(function(public_ip) {
            handler.@com.makesystem.pidgey.io.GetIpHandler::onSuccess(Ljava/lang/String;)(public_ip);			
        });

    }-*/;

}
