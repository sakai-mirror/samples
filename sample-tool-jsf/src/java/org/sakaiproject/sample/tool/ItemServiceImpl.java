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

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.thread_local.api.ThreadLocalManager;

/**
 * Implementation of the ItemService example service.
 */
public class ItemServiceImpl implements ItemService
{
	/** Our log. */
	private static Log M_log = LogFactory.getLog(ItemServiceImpl.class);

	protected ThreadLocalManager m_threadLocalManager = null;

	public void setThreadLocalManager(ThreadLocalManager manager)
	{
		m_threadLocalManager = manager;
	}

	protected ThreadLocalManager threadLocalManager()
	{
		return m_threadLocalManager;
	}

	/**
	 * Implementation of Item
	 */
	public class MyItem implements Item
	{
		protected String id;

		protected String a;

		protected String b;

		protected String c;

		public MyItem(String id, String a, String b, String c)
		{
			this.id = id;
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public String getA()
		{
			return a;
		}

		public String getB()
		{
			return b;
		}

		public String getC()
		{
			return c;
		}

		public String getId()
		{
			return id;
		}

		public void setA(String a)
		{
			this.a = a;
		}

		public void setB(String b)
		{
			this.b = b;
		}

		public void setC(String c)
		{
			this.c = c;
		}

		public void setId(String id)
		{
			this.id = id;
		}
	}

	public void init()
	{
		M_log.info("init");
	}

	public Collection getItems()
	{
		M_log.info("getItems");

		Collection items = new Vector();
		items.add(getItem("1"));
		items.add(getItem("2"));
		items.add(getItem("3"));

		return items;
	}

	public Item getItem(String id)
	{
		// cache for the request
		Item cached = (Item) threadLocalManager().get("ItemService:" + id);
		if (cached != null) return cached;

		M_log.info("getItem: " + id);

		Item rv = null;

		if (id.equals("1"))
		{
			rv = new MyItem("1", "a", "b", "c");
		}
		else if (id.equals("2"))
		{
			rv = new MyItem("2", "a2", "b2", "c2");
		}
		else if (id.equals("3"))
		{
			rv = new MyItem("3", "a3", "b3", "c3");
		}

		// cache
		if (rv != null)
		{
			threadLocalManager().set("ItemService:" + id, rv);
		}

		return rv;
	}
}
