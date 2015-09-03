package com.suappstudio.travellify.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.suappstudio.travellify.backend.utils.OfyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "travelPackageApi",
        version = "v1",
        resource = "travelPackage",
        namespace = @ApiNamespace(
                ownerDomain = "backend.travellify.suappstudio.com",
                ownerName = "backend.travellify.suappstudio.com",
                packagePath = ""
        )
)
public class TravelPackageEndpoint {

    private static final Logger logger = Logger.getLogger(TravelPackageEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;



    /**
     * Returns the {@link TravelPackage} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code TravelPackage} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "travelPackage/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public TravelPackage get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting TravelPackage with ID: " + id);
        TravelPackage travelPackage = ofy().load().type(TravelPackage.class).id(id).now();
        if (travelPackage == null) {
            throw new NotFoundException("Could not find TravelPackage with ID: " + id);
        }
        return travelPackage;
    }

    /**
     * Inserts a new {@code TravelPackage}.
     */
    @ApiMethod(
            name = "insert",
            path = "travelPackage",
            httpMethod = ApiMethod.HttpMethod.POST)
    public TravelPackage insert(TravelPackage travelPackage) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that travelPackage.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(travelPackage).now();
        logger.info("Created TravelPackage with ID: " + travelPackage.getId());

        return ofy().load().entity(travelPackage).now();
    }

    /**
     * Updates an existing {@code TravelPackage}.
     *
     * @param id            the ID of the entity to be updated
     * @param travelPackage the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code TravelPackage}
     */
    @ApiMethod(
            name = "update",
            path = "travelPackage/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public TravelPackage update(@Named("id") String id, TravelPackage travelPackage) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(travelPackage).now();
        logger.info("Updated TravelPackage: " + travelPackage);
        return ofy().load().entity(travelPackage).now();
    }

    /**
     * Deletes the specified {@code TravelPackage}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code TravelPackage}
     */
    @ApiMethod(
            name = "remove",
            path = "travelPackage/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(TravelPackage.class).id(id).now();
        logger.info("Deleted TravelPackage with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "travelPackage",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<TravelPackage> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<TravelPackage> query = ofy().load().type(TravelPackage.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<TravelPackage> queryIterator = query.iterator();
        List<TravelPackage> travelPackageList = new ArrayList<TravelPackage>(limit);
        while (queryIterator.hasNext()) {
            travelPackageList.add(queryIterator.next());
        }
        return CollectionResponse.<TravelPackage>builder().setItems(travelPackageList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(TravelPackage.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find TravelPackage with ID: " + id);
        }
    }
}