// Attach the custom sort to the FACET_NAME facet.

import com.funnelback.publicui.search.model.transaction.Facet;

// Code to run on main search
for(Facet facet : transaction.getResponse().getFacets()) {
    // Find the facet we want to set out comparator on.
    if("FACET_NAME".equals(facet.getName())) {
        facet.setCustomComparator(new com.funnelback.production.utils.CustomFacetSort(facet.getName(),transaction));
    }
}