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
        name = "travelPointApi",
        version = "v1",
        resource = "travelPoint",
        namespace = @ApiNamespace(
                ownerDomain = "backend.travellify.suappstudio.com",
                ownerName = "backend.travellify.suappstudio.com",
                packagePath = ""
        )
)
public class TravelPointEndpoint {

    private static final Logger logger = Logger.getLogger(TravelPointEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;



    /**
     * Returns the {@link TravelPoint} with the corresponding ID.
     *
     * @param chiave the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code TravelPoint} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "travelPoint/{chiave}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public TravelPoint get(@Named("chiave") Long chiave) throws NotFoundException {
        logger.info("Getting TravelPoint with ID: " + chiave);
        TravelPoint travelPoint = ofy().load().type(TravelPoint.class).id(chiave).now();
        if (travelPoint == null) {
            throw new NotFoundException("Could not find TravelPoint with ID: " + chiave);
        }
        return travelPoint;
    }

    /**
     * Inserts a new {@code TravelPoint}.
     */
    @ApiMethod(
            name = "insert",
            path = "travelPoint",
            httpMethod = ApiMethod.HttpMethod.POST)
    public TravelPoint insert(TravelPoint travelPoint) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that travelPoint.chiave has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(travelPoint).now();
        logger.info("Created TravelPoint with ID: " + travelPoint.getChiave());

        return ofy().load().entity(travelPoint).now();
    }

    /**
     * Updates an existing {@code TravelPoint}.
     *
     * @param chiave      the ID of the entity to be updated
     * @param travelPoint the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code chiave} does not correspond to an existing
     *                           {@code TravelPoint}
     */
    @ApiMethod(
            name = "update",
            path = "travelPoint/{chiave}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public TravelPoint update(@Named("chiave") Long chiave, TravelPoint travelPoint) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(chiave);
        ofy().save().entity(travelPoint).now();
        logger.info("Updated TravelPoint: " + travelPoint);
        return ofy().load().entity(travelPoint).now();
    }

    /**
     * Deletes the specified {@code TravelPoint}.
     *
     * @param chiave the ID of the entity to delete
     * @throws NotFoundException if the {@code chiave} does not correspond to an existing
     *                           {@code TravelPoint}
     */
    @ApiMethod(
            name = "remove",
            path = "travelPoint/{chiave}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("chiave") Long chiave) throws NotFoundException {
        checkExists(chiave);
        ofy().delete().type(TravelPoint.class).id(chiave).now();
        logger.info("Deleted TravelPoint with ID: " + chiave);
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
            path = "travelPoint",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<TravelPoint> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<TravelPoint> query = ofy().load().type(TravelPoint.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<TravelPoint> queryIterator = query.iterator();
        List<TravelPoint> travelPointList = new ArrayList<TravelPoint>(limit);
        while (queryIterator.hasNext()) {
            travelPointList.add(queryIterator.next());
        }
        return CollectionResponse.<TravelPoint>builder().setItems(travelPointList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long chiave) throws NotFoundException {
        try {
            ofy().load().type(TravelPoint.class).id(chiave).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find TravelPoint with ID: " + chiave);
        }
    }
}