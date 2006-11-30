/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2006 The Sakai Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.sample.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.sludge.api.Context;
import org.sakaiproject.sludge.api.Controller;
import org.sakaiproject.sludge.api.UiService;
import org.sakaiproject.util.ResourceLoader;

/**
 * A Simple Sludge Servlet Sakai Sample tool...
 */
public class SludgeTool extends HttpServlet
{
	public class One
	{
		protected String id;

		protected String one;

		protected String two;

		protected Boolean tasty;

		public Boolean getTasty()
		{
			return tasty;
		}

		public void setTasty(Boolean tasty)
		{
			this.tasty = tasty;
		}

		public One(String id, String one, String two, Boolean tasty)
		{
			this.id = id;
			this.one = one;
			this.two = two;
			this.tasty = tasty;
		}

		public String getId()
		{
			return id;
		}

		public String getOne()
		{
			return one;
		}

		public String getTwo()
		{
			return two;
		}

		public void setId(String id)
		{
			this.id = id;
		}

		public void setOne(String one)
		{
			this.one = one;
		}

		public void setTwo(String two)
		{
			this.two = two;
		}
	}

	public class Two
	{
		protected String id;

		protected String one;

		protected String three;

		protected String two;

		public Two(String id, String one, String two, String three)
		{
			this.id = id;
			this.one = one;
			this.two = two;
			this.three = three;
		}

		public String getId()
		{
			return id;
		}

		public String getOne()
		{
			return one;
		}

		public String getThree()
		{
			return three;
		}

		public String getTwo()
		{
			return two;
		}

		public void setId(String id)
		{
			this.id = id;
		}

		public void setOne(String one)
		{
			this.one = one;
		}

		public void setThree(String three)
		{
			this.three = three;
		}

		public void setTwo(String two)
		{
			this.two = two;
		}
	}

	/** Our log (commons). */
	private static Log M_log = LogFactory.getLog(SludgeTool.class);

	/** Localized messages. */
	protected static ResourceLoader messages = new ResourceLoader("sample-tool-sludge");

	/** Our self-injected ui service reference. */
	protected UiService ui = null;

	/** Our user interface A sludge. */
	protected Controller uiA = null;

	/** Our user interface B sludge. */
	protected Controller uiB = null;

	/**
	 * Shutdown the servlet.
	 */
	public void destroy()
	{
		M_log.info("destroy()");

		super.destroy();
	}

	/**
	 * Access the Servlet's information display.
	 * 
	 * @return servlet information.
	 */
	public String getServletInfo()
	{
		return "Simple Sludge Sakai Sample tool";
	}

	/**
	 * Initialize the servlet.
	 * 
	 * @param config
	 *        The servlet config.
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);

		// self-inject
		ui = (UiService) ComponentManager.get(UiService.class);

		uiA = SludgeController.constructA(ui);
		uiB = SludgeController.constructB(ui);

		M_log.info("init()");
	}

	/**
	 * Respond to requests.
	 * 
	 * @param req
	 *        The servlet request.
	 * @param res
	 *        The servlet response.
	 * @throws ServletException.
	 * @throws IOException.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		Context context = ui.prepareGet(req, res, messages, null);

		// 0 parts means the tool destination was just "/", otherwise parts[0] = "", parts[1] = the first part, etc.
		String[] parts = context.getDestination().split("/");
		String destination = null;
		if (parts.length > 1)
		{
			destination = parts[1];
		}

		M_log.info("tool destination: " + context.getDestination());

		if (destination != null && destination.equals("b") && parts.length > 2)
		{
			destinationB(req, res, context, parts[1]);
		}
		else
		{
			destinationA(req, res, context);
		}
	}

	protected void destinationA(HttpServletRequest req, HttpServletResponse res, Context context)
	{
		// two sample collections
		Collection<One> s1 = new ArrayList<One>();
		s1.add(new One("1", "1-1", "1-2", Boolean.TRUE));
		s1.add(new One("2", "2-1", "2-2", Boolean.FALSE));
		context.put("section-one-entity-list", s1);

		Collection<Two> s2 = new ArrayList<Two>();
		s2.add(new Two("1", "1-1", "1-2", "1-3"));
		s2.add(new Two("2", "2-1", "2-2", "2-3"));
		s2.add(new Two("3", "3-1", "3-2", "3-3"));
		context.put("section-two-entity-list", s2);

		// render
		ui.render(uiA, context);
	}

	protected void destinationB(HttpServletRequest req, HttpServletResponse res, Context context, String id)
	{
		// selected One
		// Note: should be id based
		One entity = new One("1", "1-1", "1-2", Boolean.TRUE);
		context.put("entityB-display", entity);

		// render
		ui.render(uiB, context);
	}

	/**
	 * Respond to requests.
	 * 
	 * @param req
	 *        The servlet request.
	 * @param res
	 *        The servlet response.
	 * @throws ServletException.
	 * @throws IOException.
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		Context context = ui.preparePost(req, res, messages, null);

		// process the incoming

		// send redirect to our current destination to complete the response
		String current = (String) context.get("sakai.destination.url");
		res.sendRedirect(res.encodeRedirectURL(current));
	}
}
