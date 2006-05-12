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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.api.ActiveTool;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.Tool;
import org.sakaiproject.tool.api.ToolSession;
import org.sakaiproject.tool.cover.SessionManager;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.util.Validator;
import org.sakaiproject.util.Web;

/**
 * <p>
 * A Simple Servlet Sakai Sample tool (number 2), which has some tool modes / destinations controlled by URL
 * </p>
 */
public class ServletTool2 extends HttpServlet
{
	/** Our log (commons). */
	private static Log M_log = LogFactory.getLog(ServletTool2.class);

	/** poor man's service - obviously, no persistence. */
	private String m_value = "unset";

	/**
	 * Access the Servlet's information display.
	 * 
	 * @return servlet information.
	 */
	public String getServletInfo()
	{
		return "Simple Servlet Sakai Sample tool (2)";
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

		M_log.info("init()");
	}

	/**
	 * Shutdown the servlet.
	 */
	public void destroy()
	{
		M_log.info("destroy()");

		super.destroy();
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
		// get the Tool session
		ToolSession toolSession = SessionManager.getCurrentToolSession();

		// take value
		String value = req.getParameter("value");
		if (value != null)
		{
			m_value = value;
		}

		// send redirect to our current destination
		String current = Web.returnUrl(req, (String) toolSession.getAttribute(ActiveTool.TOOL_ATTR_CURRENT_DESTINATION));
		res.sendRedirect(res.encodeRedirectURL(current));
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
		// get the Sakai session
		Session session = SessionManager.getCurrentSession();

		// get the Tool session
		ToolSession toolSession = SessionManager.getCurrentToolSession();

		// get the Tool
		Placement placement = ToolManager.getCurrentPlacement();

		// dispatch our destinations
		String path = req.getPathInfo();
		if (path == null) path = "/";

//		// deal (manually, for now) with null-path - last mode or home if new
//		if ("/".equals(path))
//		{
//			String currentPath = (String) toolSession.getAttribute(ActiveTool.TOOL_ATTR_CURRENT_DESTINATION);
//			if (currentPath == null)
//			{
//				currentPath = "/home";
//			}
//
//			String url = Web.returnUrl(req, currentPath);
//			res.sendRedirect(res.encodeRedirectURL(url));
//			return;
//		}

		// 0 parts means the path was just "/", otherwise parts[0] = "", parts[1] = the first part, etc.
		String[] parts = path.split("/");

		String destination = null;

		// different locations for each path
		if (parts.length > 1)
		{
			destination = parts[1];
		}

		// fragment or not?
		boolean fragment = Boolean.TRUE.toString().equals(req.getAttribute(Tool.FRAGMENT));

		// our response writer
		PrintWriter out = null;

		if (!fragment)
		{
			// write a full HTML header
			res.setContentType("text/html; charset=UTF-8");
			res.addDateHeader("Expires", System.currentTimeMillis() - (1000L * 60L * 60L * 24L * 365L));
			res.addDateHeader("Last-Modified", System.currentTimeMillis());
			res.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
			res.addHeader("Pragma", "no-cache");

			out = res.getWriter();
			out
					.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
			out.println("<head>");
			out.println("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />");

			// include the portal's stuff in head (css and js links)
			String headInclude = (String) req.getAttribute("sakai.html.head");
			if (headInclude != null)
			{
				out.println(headInclude);
			}

			// use our placement title, with our destination
			out.print("<title>");
			if (placement != null)
			{
				out.print(Validator.escapeHtml(placement.getTitle()));
				if (destination != null)
				{
					out.print(" - " + Validator.escapeHtml(destination));
				}
			}
			out.println("</title>");

			out.print("</head><body");

			// do the body the portal wants to do
			String onload = (String) req.getAttribute("sakai.html.body.onload");
			if (onload != null)
			{
				out.print(" onload=\"" + onload + "\"");
			}
			out.println(">");

		}
		else
		{
			out = res.getWriter();
		}

		out.println("<div class=\"portletBody\">");

		// show the current user session information (for the user across all Sakai tools)
		if (session == null)
		{
			out.println("no session established");
			out.println("<br />");
		}
		else
		{
			out.println("session id: " + session.getId());
			out.println("<br />");
			out.println("session user id: " + session.getUserId());
			out.println("<br />");
			out.println("session user eid: " + session.getUserEid());
			out.println("<br />");
			out.println("session started: " + DateFormat.getDateInstance().format(new Date(session.getCreationTime())));
			out.println("<br />");
			out.println("session accessed: " + DateFormat.getDateInstance().format(new Date(session.getLastAccessedTime())));
			out.println("<br />");
			out.println("session inactive after: " + session.getMaxInactiveInterval());
			out.println("<br />");
		}

		// show the tool session (for this tool placement / user)
		if (toolSession == null)
		{
			out.println("no tool session established");
			out.println("<br />");
		}
		else
		{
			out.println("tool session id: " + toolSession.getId());
			out.println("<br />");
			out.println("tool session started: " + DateFormat.getDateInstance().format(new Date(toolSession.getCreationTime())));
			out.println("<br />");
			out.println("tool session last accessed: "
					+ DateFormat.getDateInstance().format(new Date(toolSession.getLastAccessedTime())));
			out.println("<br />");
		}

		// show the placement information
		if (placement == null)
		{
			out.println("no placement");
			out.println("<br />");
		}
		else
		{
			out.println("placement id: " + placement.getId());
			out.println("<br />");
			out.println("placement context: " + placement.getContext());
			out.println("<br />");
			out.println("placement title: " + placement.getTitle());
			out.println("<br />");
			out.println("placement tool: " + placement.getToolId());
			// placement.getConfig();
			// placement.getPlacementConfig();
			out.println("<br />");
		}

		// show value
		out.println("<p>Value: " + m_value + "</p>");

		// different locations for each path
		if (destination == null)
		{
			out.println("<p>no destination</p>");
		}
		else
		{
			out.println("<p>destination: " + destination + "</p>");
		}

		out.println("</div>");

		// link to some destinations
		String home = Web.returnUrl(req, "/home");
		String destA = Web.returnUrl(req, "/a");
		String destB = Web.returnUrl(req, "/b/123-45-6789");
		out.println("<p>navigation<ul><li><a href=\"" + home + "\">home</a></li><li><a href=\"" + destA
				+ "\">a</a></li><li><a href=\"" + destB + "\">b</a></li></ul></p>");

		// a data posting
		out
				.println("<p><form method=\"post\" action=\""
						+ destB
						+ "\">value:<input name=\"value\" id=\"value\" type=\"text\" /><input name=\"submit\" type=\"submit\" id=\"submit\" value=\"submit\" /></form></p>");

		Web.snoop(out, true, getServletConfig(), req);

		if (!fragment)
		{
			// end the complete document
			out.println("</body></html>");
		}
		
//		// keep track (manually, for now) of our current destination
//		toolSession.setAttribute(ActiveTool.TOOL_ATTR_CURRENT_DESTINATION, path);
	}
}
