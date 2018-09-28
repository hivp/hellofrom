package com.kn.task;

import io.vertx.core.AbstractVerticle;

public class HelloFrom extends AbstractVerticle {

  //In case we are in K8s, this variable will be injected by the yaml
	private static final String podName = System.getenv("K8S_POD_NAME");

	private static String message = null;

  /**
	 * Singleton to load the message just one time
	 * @return the message to be displayed
	 */
	private static synchronized String getSingletonString() {
		if(message==null) {
			if(podName == null) { //we are not in K8s
				message = "Hello from foobar\n";
			}
			else{//we are in K8s
				message = "Hello from " + podName + "\n";
			}
		}
		return message;
	}

  @Override
  public void start() {

    vertx.createHttpServer()
        .requestHandler(req -> req.response().end(this.getSingletonString()))
        .listen(8080);
  }

}
