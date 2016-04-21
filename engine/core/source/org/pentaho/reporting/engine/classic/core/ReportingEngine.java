/*
 * This program is free software; you can redistribute it and/or modify it under the
 *  terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 *  Foundation.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this
 *  program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *  or from the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  Copyright (c) 2006 - 2015 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.reporting.engine.classic.core;

import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bugg on 20/04/16.
 */
public class ReportingEngine implements ReportingInterface {

  public ReportingEngine() {
    System.out.println("hello");
  }

  public void init(){
    System.out.print("hello again");
  }

  @Override
  public String createReport(String reportBytes) {
    final ResourceManager mgr = new ResourceManager();

    URL url = null;
    try {
      url = new File("/tmp/basic_sample.prpt").toURI().toURL();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }


    MasterReport report = null;
    try {
      report = (MasterReport) mgr.createDirectly(url, MasterReport.class).getResource();
    } catch (ResourceException e) {
      e.printStackTrace();
    }


    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bos, true);

    try {
      HtmlReportUtil.createStreamHTML(report, ps);
    } catch (ReportProcessingException e) {
      e.printStackTrace();
    }
    return  bos.toString();
  }
}
