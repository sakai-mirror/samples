/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2007 The Sakai Foundation.
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

import org.sakaiproject.sludge.api.Controller;
import org.sakaiproject.sludge.api.UiService;

/**
 * A Simple Sludge Servlet Sakai Sample tool...
 */
public class SludgeController
{
	// Note: do not allow an automatic formatter to format this file! -ggolden
	public static Controller constructA(UiService ui)
	{
		return
			ui.newInterface()
				.setTitle("ui-title")
				.setHeader("ui-header")
				.add(
					ui.newSection()
						.setTitle("section-one-title")
						.add(
							ui.newInstructions()
								.setText("section-one-instructions"))
						.add(
							ui.newEntityList()
								.setIterator(ui.newPropertyReference().setEntityReference("section-one-entity-list"), ui.newContextReference().setName("entity"))
								.setTitle("section-one-list-title")
								.addColumn(
									ui.newPropertyColumn()
										.setTitle("s1-c1-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setEntityReference("entity")
												.setPropertyReference("one"))
										.setEntityNavigation(
											ui.newEntityNavigation()
												.setDestination(ui.newDestination().setDestination("/b/{0}", ui.newTextPropertyReference().setEntityReference("entity").setPropertyReference("id")))))
								.addColumn(
									ui.newPropertyColumn()
										.setTitle("s1-c2-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setEntityReference("entity").setPropertyReference("two"))
										.addFootnote(
											ui.newFootnote()
												.setText("section-one-list-footnote")
												.setCriteria(
													ui.newDecision()
														.setProperty(
															ui.newPropertyReference()
																.setEntityReference("entity").setPropertyReference("tasty")))))))
				.add(
					ui.newSection()
						.setTitle("section-two-title")
						.add(
							ui.newInstructions()
								.setText("section-two-instructions"))
						.add(
							ui.newEntityList()
								.setIterator(ui.newPropertyReference().setEntityReference("section-two-entity-list"), ui.newContextReference().setName("entity"))
								.setTitle("section-two-list-title")
								.addColumn(
									ui.newPropertyColumn()
										.setTitle("s2-c1-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setEntityReference("entity").setPropertyReference("one"))
										.setEntityNavigation(
												ui.newEntityNavigation()
													.setDestination(ui.newDestination().setDestination("/c/{0}", ui.newTextPropertyReference().setEntityReference("entity").setPropertyReference("id")))))
								.addColumn(
									ui.newPropertyColumn()
										.setTitle("s2-c2-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setEntityReference("entity").setPropertyReference("two")))
								.addColumn(
									ui.newPropertyColumn()
										.setTitle("s2-c3-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setEntityReference("entity").setPropertyReference("three")))));
	}

	public static Controller constructB(UiService ui)
	{
		return
			ui.newInterface()
				.setTitle("uiB-title")
				.setHeader("uiB-header")
				.add(
					ui.newSection()
						.add(
							ui.newInstructions()
								.setText("sectionB-instructions"))
						.add(
							ui.newEntityDisplay()
								.setEntityReference(ui.newPropertyReference().setEntityReference("entityB-display"))
								.setTitle("sectionB-display-title")
								.addRow(
									ui.newPropertyRow()
										.setTitle("r1B-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setPropertyReference("id")))
								.addRow(
									ui.newPropertyRow()
										.setTitle("r2B-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setPropertyReference("one")))
								.addRow(
									ui.newPropertyRow()
										.setTitle("r3B-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setPropertyReference("two")))
								.addRow(
									ui.newPropertyRow()
										.setTitle("r4B-title")
										.setProperty(
											ui.newTextPropertyReference()
												.setPropertyReference("tasty"))))
								.add(
									ui.newSection()
										.setTitle("buttons-title")
										.add(
											ui.newNavigation()
												.setTitle("button-one-title")
												.setDestination(ui.newDestination().setDestination("/c/$ID", ui.newPropertyReference().setEntityReference("entityB-display").setPropertyReference("id"))))
										.add(
												ui.newNavigation()
													.setTitle("button-two-title")
													.setDestination(ui.newDestination().setDestination("/")))));
	}
}

