/*
 * Copyright 2014 Yubico.
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://developers.google.com/open-source/licenses/bsd
 */

package com.yubico.u2f.tools.httpserver.servlets;

import java.io.IOException;
import java.io.PrintStream;

import com.yubico.u2f.U2fException;
import com.yubico.u2f.server.U2fServer;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.yubico.u2f.server.messages.SignResponse;

public class SignFinishServlet extends HtmlServlet {

  private final U2fServer u2fServer;

  public SignFinishServlet(U2fServer u2fServer) {
    this.u2fServer = u2fServer;
  }

  @Override
  public void generateBody(Request req, Response resp, PrintStream body) {
    SignResponse signResponse = new SignResponse(
        req.getParameter("clientData"),
        req.getParameter("signData"),
        req.getParameter("challenge"),
        req.getParameter("appId"));
    try {
      u2fServer.processSignResponse(signResponse);
      body.println("Success!!!");
    } catch (U2fException e) {
      body.println("Failure: " + e.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
