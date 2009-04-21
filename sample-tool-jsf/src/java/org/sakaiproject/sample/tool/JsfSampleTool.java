/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.sample.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.jsf.util.JsfTool;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.cover.SessionManager;

/**
 * <p>
 * Example of extending the standard JsfTool for a particular application; in this case, the sample JSF tool.
 * </p>
 */
public class JsfSampleTool extends JsfTool
{
	/** Our log (commons). */
	private static Log M_log = LogFactory.getLog(JsfSampleTool.class);

	/**
	 * Compute a target (i.e. the servlet path info, not including folder root or jsf extension) for the case of the actual path being empty.
	 * 
	 * @return The servlet info path target computed for the case of empty actual path.
	 */
	protected String computeDefaultTarget()
	{
		Session session = SessionManager.getCurrentSession();
		if (session.getUserId() == null)
		{
			return "list";
		}

		return "main";
	}
}
