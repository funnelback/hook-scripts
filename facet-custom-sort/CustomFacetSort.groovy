package com.funnelback.production.utils;

/*
 * Implements custom sorting for faceted navigation
 * @author: Peter Levan, May 2018
 */

import java.util.Comparator;

import com.funnelback.publicui.search.model.transaction.Facet.CategoryValue;
import com.funnelback.publicui.search.model.transaction.SearchTransaction;

public class CustomFacetSort implements Comparator<CategoryValue> {

    String facetName;
    SearchTransaction transaction;

    def mode="custom"; // Default to alpabetic sorting if nothing is set in collection.cfg
    def labelOrderMap = [:];

    public CustomFacetSort(String facetName, SearchTransaction transaction) {
        this.facetName = facetName;
        this.transaction = transaction;

        // Read the mode and custom category order from collection.cfg
        if (transaction.question.collection.configuration.value("faceted_navigation."+facetName+".sort_mode") != null) {
            mode = transaction.question.collection.configuration.value("faceted_navigation."+facetName+".sort_mode")
            //debug: transaction.response.customData["mode"] = mode
            if (transaction.question.collection.configuration.value("faceted_navigation."+facetName+".sort_order.first") != null) {
                // build a list of items to place at the start of the sort
                def first = transaction.question.collection.configuration.value("faceted_navigation."+facetName+".sort_order.first").split('\\|')
                //debug: transaction.response.customData["first"] = first
                for (def i=0; i<first.size(); i++) {
                    if (mode == "custom") {
                        labelOrderMap[first[i].toLowerCase()] = '\u0000\u0000\u0000' + String.format("%06d",i)
                    }
                    else if (mode == "dcustom") {
                        labelOrderMap[first[i].toLowerCase()] = '\uFFFE\uFFFE\uFFFE' + String.format("%06d",first.size()-i)
                    }
                }
            }
                        if (transaction.question.collection.configuration.value("faceted_navigation."+facetName+".sort_order.last") != null) {
                // build a list of items to place at the end of the sort
                def last = transaction.question.collection.configuration.value("faceted_navigation."+facetName+".sort_order.last").split('\\|')

                for (def i=0; i<last.size(); i++) {
                    if (mode == "custom") {
                        labelOrderMap[last[i].toLowerCase()] = '\uFFFE\uFFFE\uFFFE' + String.format("%06d",i)
                    }
                    else if (mode == "dcustom") {
                        labelOrderMap[last[i].toLowerCase()] = '\u0000\u0000\u0000' + String.format("%06d",last.size()-i)
                    }
                }
            }
        }
    }

    @Override
    public int compare(CategoryValue o1, CategoryValue o2) {
        // lowercase the label as we don't want the sort to be case sensitive.
        def labela = o1.getLabel().toLowerCase(), labelb = o2.getLabel().toLowerCase()

        if (labelOrderMap.containsKey(labela)) {
            labela = labelOrderMap[labela]
        }
        if (labelOrderMap.containsKey(labelb)) {
            labelb = labelOrderMap[labelb]
        }

        if (mode == "custom") {
            return labela.compareTo(labelb);
        } else {
            return labelb.compareTo(labela);
        }
    }
}