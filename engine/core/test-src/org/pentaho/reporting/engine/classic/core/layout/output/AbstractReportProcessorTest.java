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
 *  Copyright (c) 2006 - 2016 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.reporting.engine.classic.core.layout.output;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.event.ReportProgressEvent;
import org.pentaho.reporting.engine.classic.core.event.ReportProgressListener;
import org.pentaho.reporting.engine.classic.core.function.OutputFunction;
import org.pentaho.reporting.engine.classic.core.testsupport.dummyoutput.DummyReportProcessor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AbstractReportProcessorTest {

  @BeforeClass
  public static void setUp() throws Exception {
    ClassicEngineBoot.getInstance().start();
  }

  @Test
  public void testConsrtruction() throws Exception {
    boolean thrown1 = false;
    try {
      new DummyReportProcessor( null );
    } catch ( final NullPointerException e ) {
      thrown1 = true;
    }
    assertEquals( true, thrown1 );
    boolean thrown2 = false;
    try {
      new AbstractReportProcessor( new MasterReport(), null ) {

        @Override protected OutputFunction createLayoutManager() {
          return null;
        }
      };
    } catch ( final NullPointerException e ) {
      thrown2 = true;
    }
    assertEquals( true, thrown2 );


  }

  @Test
  public void addReportProgressListener() throws Exception {
    final AbstractReportProcessor reportProcessor = new DummyReportProcessor( new MasterReport() );
    boolean thrown1 = false;
    try {
      reportProcessor.addReportProgressListener( null );
    } catch ( final NullPointerException e ) {
      thrown1 = true;
    }
    assertEquals( true, thrown1 );
  }

  @Test
  public void addRemoveCallListener() throws Exception {
    final AbstractReportProcessor reportProcessor = new DummyReportProcessor( new MasterReport() );
    boolean thrown1 = false;
    try {
      reportProcessor.removeReportProgressListener( null );
    } catch ( final NullPointerException e ) {
      thrown1 = true;
    }
    assertEquals( true, thrown1 );
    final ReportProgressListener mock = mock( ReportProgressListener.class );
    reportProcessor.removeReportProgressListener( mock );
    reportProcessor.addReportProgressListener( mock );
    final ReportProgressEvent state = new ReportProgressEvent( new Object() );
    reportProcessor.fireProcessingStarted( state );
    reportProcessor.fireStateUpdate( state );
    reportProcessor.fireProcessingFinished( state );
    verify( mock, times( 1 ) ).reportProcessingStarted( state );
    verify( mock, times( 1 ) ).reportProcessingUpdate( state );
    verify( mock, times( 1 ) ).reportProcessingFinished( state );
    reportProcessor.removeReportProgressListener( mock );
    reportProcessor.fireProcessingStarted( state );
    reportProcessor.fireStateUpdate( state );
    reportProcessor.fireProcessingFinished( state );
    verify( mock, times( 1 ) ).reportProcessingStarted( state );
    verify( mock, times( 1 ) ).reportProcessingUpdate( state );
    verify( mock, times( 1 ) ).reportProcessingFinished( state );
  }


}
