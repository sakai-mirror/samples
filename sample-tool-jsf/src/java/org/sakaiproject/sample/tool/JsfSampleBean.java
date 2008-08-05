/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright 2006 Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
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

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Sample JSF UI controller bean for the sample jsf tool.
 * </p>
 */
public class JsfSampleBean
{
	/** Our log (commons). */
	private static Log M_log = LogFactory.getLog(JsfSampleBean.class);

	/** Our service, injected. */
	protected ItemService itemService;

	/** This needs to hang around for the session, between response and request. */
	protected DataModel items = null;

	/**
	 * Access our item service.
	 * 
	 * @return The injected item service.
	 */
	public ItemService getItemService()
	{
		return itemService;
	}

	/**
	 * Set the item service - used to inject the item service.
	 * 
	 * @param itemService
	 *        the item service to use.
	 */
	public void setItemService(ItemService itemService)
	{
		M_log.info("setItemSevice: " + itemService);

		this.itemService = itemService;
	}

	/**
	 * Construct.
	 */
	public JsfSampleBean()
	{
		M_log.info("constructed");
	}

	/**
	 * An action method to process and select the navigation for a UI control (button, link).<br />
	 * Used for a button of the main page.
	 * 
	 * @return The navigation outcome string.
	 */
	public String getNextStep()
	{
		M_log.info("getNextStep: returning 'list'");
		return "list";
	}

	/**
	 * An action method to process and select the navigation for a UI control (button, link).<br />
	 * Used for the tool bar in the main page.
	 * 
	 * @return The navigation outcome string.
	 */
	public String processToolBarItem()
	{
		M_log.info("processToolBarItem: returning 'list'");
		return "list";
	}

	/**
	 * Get data items (entities), wrapped in UI controllers, for a JSF data table.
	 * 
	 * @return The items for the table.
	 */
	public DataModel getItems()
	{
		// if we have not yet computed, do so
		if (this.items == null)
		{
			setItems(computeItems());
		}

		return this.items;
	}

	/**
	 * Set the data items.
	 * 
	 * @param items
	 *        Our data items.
	 */
	public void setItems(DataModel items)
	{
		this.items = items;
	}

	/**
	 * Create the DataModel for the UI table component that has our UI controller objects for our item collection.
	 * 
	 * @return The items for the table.
	 */
	protected DataModel computeItems()
	{
		M_log.info("computeItems");

		// collect information from the appropriate service
		Collection items = getItemService().getItems();

		// wrap the entities in controllers
		Collection wrappers = new Vector();
		for (Iterator i = items.iterator(); i.hasNext();)
		{
			wrappers.add(new ItemController((Item) i.next()));
		}

		// wrap a JSF object around our controllers
		DataModel rv = new ListDataModel();
		rv.setWrappedData(wrappers);

		return rv;
	}

	/**
	 * This is a UI controller class that wraps the item's properties and methods,<br />
	 * adding what we need to hook up with the UI components.
	 */
	public class ItemController
	{
		/** We store only a reference (id), not the entire item (which would be large and caching). */
		protected String itemId = null;

		/** Set when this item was selected in the interface. */
		boolean selected = false;

		/** Set when this item was clicked on from the interface. */
		boolean clicked = false;

		public ItemController(Item item)
		{
			this.itemId = item.getId();
			this.selected = false;
		}

		/**
		 * When we need an Item, we get it from the service. Thread-local caching in the service is appreciated.
		 * 
		 * @return The item associated with our stored id.
		 */
		protected Item getItem()
		{
			return itemService.getItem(itemId);
		}

		public boolean isClicked()
		{
			return clicked;
		}

		public void setClicked(boolean clicked)
		{
			this.clicked = clicked;
		}

		/**
		 * Action method on the item.
		 * 
		 * @return The next navigation outcome value.
		 */
		public String processClick()
		{
			M_log.info("processClick: id: " + itemId);
			setClicked(true);
			return "itemClick";
		}

		public String getA()
		{
			return getItem().getA();
		}

		public String getB()
		{
			return getItem().getB();
		}

		public String getC()
		{
			return getItem().getC();
		}

/*		public void setA(String a)
		{
			getItem().setA(a);
		}

		public void setB(String b)
		{
			getItem().setB(b);
		}

		public void setC(String c)
		{
			getItem().setC(c);
		}
*/

		public boolean isSelected()
		{
			return selected;
		}

		public void setSelected(boolean selected)
		{
			M_log.info("setSelected: id: " + itemId + " selected: " + selected);
			this.selected = selected;
		}

		public String getId()
		{
			return itemId;
		}

/*		public void setId(String id)
		{
			getItem().setId(id);
		}
*/
	}
}
