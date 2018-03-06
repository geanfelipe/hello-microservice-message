package io.vertx.book.message;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class HelloMicroservice extends AbstractVerticle {

    @Override
    public void start() {
    	//receive message from the address 'hello' 
    	vertx.eventBus().<String>consumer("hello", message -> {
    		double chaos = Math.random();
    		
    		JsonObject json = new JsonObject()
    				.put("served-by",this.toString());
    		System.out.println("novo request chegando de "+message.address()+ " : "+message.replyAddress());
    		
    		System.out.println("time: "+chaos);
    		if(chaos < 0.6) {
    			//normal behavior 
    			//check the whether we have reveived a payload in the incoming message
        		if(message.body().isEmpty()) {
        			message.reply(json.put("message", "hello"));
        		}else {
        			message.reply(json.put("message","hello "+message.body()));
        		}
    		}else if(chaos < 0.9) {
    			System.out.println("returning a feilure");
    			message.fail(500, "message processing a failure");
    		} else {
    			System.out.println("not reply");
    			//not reply, leading to a timeout on the consumer side
    		}
    		
    	});
    }
}
