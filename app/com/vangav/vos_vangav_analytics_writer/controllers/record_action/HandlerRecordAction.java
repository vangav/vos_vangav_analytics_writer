/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.vos_vangav_analytics_writer.controllers.record_action;

import java.util.Calendar;

import com.vangav.backend.cassandra.formatting.CalendarFormatterInl;
import com.vangav.backend.play_framework.request.Request;
import com.vangav.backend.play_framework.request.RequestJsonBody;
import com.vangav.backend.play_framework.request.response.ResponseBody;
import com.vangav.vos_vangav_analytics_writer.actions.ActionsManager;
import com.vangav.vos_vangav_analytics_writer.cassandra_keyspaces.v_analytics.AnnualActionCounters;
import com.vangav.vos_vangav_analytics_writer.cassandra_keyspaces.v_analytics.DailyActionCounters;
import com.vangav.vos_vangav_analytics_writer.cassandra_keyspaces.v_analytics.MonthlyActionCounters;
import com.vangav.vos_vangav_analytics_writer.cassandra_keyspaces.v_analytics.TotalActionCounters;
import com.vangav.vos_vangav_analytics_writer.controllers.CommonPlayHandler;

/**
 * GENERATED using ControllersGeneratorMain.java
 */
/**
 * HandlerRecordAction
 *   handles request-to-response processing
 *   also handles after response processing (if any)
 * */
public class HandlerRecordAction extends CommonPlayHandler {

  private static final String kName = "RecordAction";

  @Override
  protected String getName () {

    return kName;
  }

  @Override
  protected RequestJsonBody getRequestJson () {

    return new RequestRecordAction();
  }

  @Override
  protected ResponseBody getResponseBody () {

    return new ResponseRecordAction();
  }

  @Override
  protected void processRequest (final Request request) throws Exception {
    
    // actual processing happens in after processing
  }

  @Override
  protected void afterProcessing (
    final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestRecordAction requestRecordAction =
      (RequestRecordAction)request.getRequestJsonBody();
    
    String action =
      ActionsManager.format(
        requestRecordAction.class_prefix,
        requestRecordAction.action_id);
    
    AnnualActionCounters.i().executeAsyncIncrement(
      CalendarFormatterInl.concatCalendarFields(
        request.getStartCalendar(),
        Calendar.YEAR)
      + "_"
      + action);
    
    DailyActionCounters.i().executeAsyncIncrement(
      CalendarFormatterInl.concatCalendarFields(
        request.getStartCalendar(),
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_MONTH)
      + "_"
      + action);
    
    MonthlyActionCounters.i().executeAsyncIncrement(
      CalendarFormatterInl.concatCalendarFields(
          request.getStartCalendar(),
          Calendar.YEAR,
          Calendar.MONTH)
        + "_"
        + action);
    
    TotalActionCounters.i().executeAsyncIncrement(action);
  }
}
