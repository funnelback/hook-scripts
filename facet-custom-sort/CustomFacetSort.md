# Custom sort for faceted navigation

This is a custom sort comparator for use with Funnelback Faceted Navigation in Funnelback v15.12+

This implements custom sorting as defined in the [faceted navigation documentation](https://docs.funnelback.com/15.14/customise/standard-options/faceted-navigation/faceted_navigation_custom_sort.html) and the same custom sort that was supported in the Faceted Navigation v2 code (see the [faceted-navigation](https://github.com/funnelback/faceted-navigation) GitHub project).

Allows configuration of custom sort via `collection.cfg` settings:

## `collection.cfg` options


### Sort order - first elements

	faceted_navigation.<FACET NAME>.sort_order.first=<Pipe delimited list of categories>

* Defines the list of categories, pipe delimited, to place at the top of the listed facet categories.

### Sort order - last elements

	faceted_navigation.<FACET NAME>.sort_order.last=<Pipe delimited list of categories>

* Defines the list of categories, pipe delimited, to place at the bottom of the listed facet categories.

### Sort mode - sorting of unspecified values

	faceted_navigation.<FACET NAME>.sort_mode=<custom|dcustom>

* `custom` sorts the facets in a custom order (defined by the first and last options) with unspecified items sorted alphabetically
* `dcustom` sorts the facets in a custom order (defined by the first and last options) with unspecified items sorted reverse alphabetically

## Setup

1. Download the `CustomFacetSort.groovy` file and add this to the following location: `$SEARCH_HOME/conf/COLLECTION_NAME/@groovy/com/funnelback/production/utils/CustomFacetSort.groovy`

2. Attach the custom sort to the desired facet in the collection's post process hook script. (See the example `hook_pre_process.groovy` script)

3. Edit the sort options for the facet in the administration interface and configure the facet to use the custom sort.

4. Define `collection.cfg` settings to control the custom sort order.

See also: [faceted navigation documentation](https://docs.funnelback.com/15.14/customise/standard-options/faceted-navigation/faceted_navigation_custom_sort.html).
