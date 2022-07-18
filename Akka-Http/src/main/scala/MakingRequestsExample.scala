package org.example.application

/*
  request-level API

  https://doc.akka.io/docs/akka-http/current/client-side/request-level.html

  Most often, your HTTP client needs are very basic. You need the HTTP response for a certain request and don’t want to
  bother with setting up a full-blown streaming infrastructure.

  For these cases Akka HTTP offers the Http().singleRequest(...) method, which turns an HttpRequest instance into
  Future[HttpResponse]. Internally the request is dispatched across the (cached) host connection pool for the request’s
  effective URI.
 */
object MakingRequestsExample {

}
