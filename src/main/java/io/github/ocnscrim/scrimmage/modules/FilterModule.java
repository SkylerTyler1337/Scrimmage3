/*
 * The MIT License
 *
 * Copyright 2013 OCN Scrim Plugin Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.ocnscrim.scrimmage.modules;

import io.github.ocnscrim.scrimmage.filters.Filter;
import io.github.ocnscrim.scrimmage.map.Map;
import io.github.ocnscrim.scrimmage.match.Match;
import io.github.ocnscrim.scrimmage.utils.XMLUtils;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Controller class for filters
 *
 * @author msalihov (Maxim Salikhov)
 */
public class FilterModule extends Module {

	private final List<Filter> filterList;

	/**
	 * Basic constructor with parts inherited from superclass
	 *
	 * @param mat
	 * @param map
	 */
	public FilterModule(Match mat, Map map) {
		super(mat, map);
		filterList = new ArrayList<>();
		Node node = XMLUtils.getFirstNodeByName(document.getDoc(), "filters");
		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				NodeList nodeChildren = element.getChildNodes();
				for (int count = 0; count < nodeChildren.getLength(); count++) {
					Node childNode = nodeChildren.item(count);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						if (childElement.getTagName().equals("filter")) {
							String childElementNameAttr = childElement.getAttribute("name");
							if (childElement.hasAttribute("parents")) {
								String childElementParentsAttr = childElement.getAttribute("parents");
								filterList.add(new Filter(childElement, childElementNameAttr, childElementParentsAttr));
							} else {
								filterList.add(new Filter(childElement, childElementNameAttr));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Returns a list with all defined filters
	 *
	 * @return List with all filters
	 */
	public List<Filter> getFilters() {
		return filterList;
	}

}
