/*
 * Copyright (c) 2011, salesforce.com, inc.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of salesforce.com, inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of salesforce.com, inc.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.salesforce.androidsdk.util;

import android.database.Observable;

/**
 * This allows other classes (primarily test classes) to register & receive events generated by the sdk.
 */
public class EventsObservable extends Observable<EventsObserver> {

    public static enum EventType {
    	AppCreateComplete,
    	AppLocked,
    	AppUnlocked,
    	RenditionComplete,
    	LoginWebViewPageFinished,
    	LogoutComplete,
    	GapWebViewPageFinished,
    	Other
    }

    public static class Event {
    	private EventType type;
		private String data;

		public Event(EventType eventType) {
			this(eventType, null);
		}		
		
		public Event(EventType type, String data) {
    		this.type = type;
    		this.data = data;
    	}
    
		public EventType getType() {
			return type;
		}
		
		public String getData() {
			return data;
		}
		
    }
    
    public interface Listener {
    	void onActivityEvent(EventType evt);
    }
    
    private static final EventsObservable instance = new EventsObservable();
    
    public static EventsObservable get() {
        return instance;
    }

    public void notifyEvent(EventType type) {
    	notifyEvent(new Event(type));
    }
    
    public void notifyEvent(Event evt) {
        synchronized(mObservers) {
            for (EventsObserver o : mObservers)
                o.onEvent(evt);
        }
    }
}
