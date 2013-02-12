/**
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_9;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openmrs.LocationAttributeType;
import org.openmrs.annotation.Handler;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * Allows standard CRUD for the {@link LocationAttributeType} domain object
 */
@Resource(name = "locationattributetype", supportedClass = LocationAttributeType.class, supportedOpenmrsVersions = "1.9.*")
@Handler(supports = LocationAttributeType.class, order = 0)
public class LocationAttributeTypeResource extends BaseAttributeTypeCrudResource<LocationAttributeType> {
	
	public LocationAttributeTypeResource() {
	}
	
	private LocationService service() {
		return Context.getLocationService();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getByUniqueId(java.lang.String)
	 */
	@Override
	public LocationAttributeType getByUniqueId(String uniqueId) {
		return service().getLocationAttributeTypeByUuid(uniqueId);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doGetAll(org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	protected NeedsPaging<LocationAttributeType> doGetAll(RequestContext context) throws ResponseException {
		return new NeedsPaging<LocationAttributeType>(service().getAllLocationAttributeTypes(), context);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#newDelegate()
	 */
	@Override
	public LocationAttributeType newDelegate() {
		return new LocationAttributeType();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#save(java.lang.Object)
	 */
	@Override
	public LocationAttributeType save(LocationAttributeType delegate) {
		return service().saveLocationAttributeType(delegate);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#purge(java.lang.Object,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public void purge(LocationAttributeType delegate, RequestContext context) throws ResponseException {
		service().purgeLocationAttributeType(delegate);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doSearch(java.lang.String,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	protected NeedsPaging<LocationAttributeType> doSearch(String query, RequestContext context) {
		// TODO: Should be a LocationAttributeType search method in LocationService
		List<LocationAttributeType> allAttrs = service().getAllLocationAttributeTypes();
		List<LocationAttributeType> queryResult = new ArrayList<LocationAttributeType>();
		for (LocationAttributeType locAttr : allAttrs) {
			if (Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE).matcher(locAttr.getName()).find()) {
				queryResult.add(locAttr);
			}
		}
		return new NeedsPaging<LocationAttributeType>(queryResult, context);
	}
}