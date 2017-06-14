package com.cccis.base.elasticsearch.operation;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import com.cccis.base.elasticsearch.BaseField;
import com.cccis.base.elasticsearch.ClientInstance;
import com.cccis.base.elasticsearch.JsonUtils;

/**
 * for the operation, add, get, delete
 */
public class CrudOperation {

    private String index;
    private String type;

    public CrudOperation(String index, String type) {
        this.index = index;
        this.type = type;
    }

    /**
     *
     * @param jsonString   json string
     * @return
     */
    public AddMessage add(String jsonString) {
        return add(new AddElement(null, jsonString));
    }

    /**
     *
     * @param jsonList
     * @return
     */
    public AddMessage add(List<String> jsonList) {
        List<AddElement> elements = Lists.newArrayList();
        for (String str : jsonList) {
            elements.add(new AddElement(null, str));
        }
        return addObjectForElement(elements);
    }

    /**
     *
     * @param id    id of document
     * @param jsonString   json string
     * @return
     */
    public AddMessage add(String id, String jsonString) {
        return add(new AddElement(id, jsonString));
    }

    /**
     *
     * @param e
     * @return
     */
    private AddMessage add(AddElement e) {
        AddMessage messages = AddMessage.createMessages();
        IndexResponse response = ClientInstance.getClient().prepareIndex(index, type, e.id)
                .setSource(e.jsonString).get();
        if (!response.isCreated())
            messages.addFailure(new FailureMessage(0, "failure"));
        return messages;
    }

    /**
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T extends BaseField> AddMessage addObject(T obj) {
        return add(obj.getId(), JsonUtils.toString(obj));
    }

    /**
     *
     * @param list
     * @return
     */
    private AddMessage addObjectForElement(List<AddElement> list) {
        AddMessage messages = AddMessage.createMessages();
        if (list == null || list.isEmpty())
            return messages;
        BulkRequestBuilder bulkRequest = ClientInstance.getClient().prepareBulk();

        for (AddElement e : list) {
            bulkRequest.add(ClientInstance.getClient().prepareIndex(index, type, e.id).setSource(e.jsonString));
        }
        BulkResponse bulkResponse = bulkRequest.get();

        if (bulkResponse.hasFailures()) {
            for (Iterator<BulkItemResponse> iter = bulkResponse.iterator(); iter.hasNext(); ) {
                BulkItemResponse item = iter.next();
                if (item.isFailed())
                    messages.addFailure(new FailureMessage(item.getItemId(), item.getFailureMessage()));
            }
        }
        return messages;
    }

    /**
     *
     * @param records
     * @param <T>
     * @return
     */
    public <T extends BaseField> AddMessage addObject(List<T> records) {
        List<AddElement> elements = Lists.newArrayList();
        for (T record : records) {
            elements.add(new AddElement(record.getId(), JsonUtils.toString(record)));
        }
        return addObjectForElement(elements);
    }

    private class AddElement {

        String id;
        String jsonString;

        public AddElement(String id, String jsonString) {
            this.id = id;
            this.jsonString = jsonString;
        }
    }

    /**
     * get json data by id array
     * @param ids
     * @return
     */
    public GetMessage get(String... ids) {
        GetMessage message = GetMessage.createMessages();
        if (ids == null || ids.length == 0)
            return message;
        for (String id : ids) {
            GetResponse response = ClientInstance.getClient().prepareGet(index, type, id).get();
            message.addElement(response.getSourceAsString());
        }
        return message;
    }

    /**
     * delete data by id array
     * @param ids
     * @return
     */
    public DeleteMessage delete(String... ids) {
        DeleteMessage message = DeleteMessage.createMessages();
        if (ids == null || ids.length == 0)
            return message;
        for (String id : ids) {
            DeleteResponse response = ClientInstance.getClient().prepareDelete(index, type, id).get();
            if (!response.isFound())
                message.addFailure(response.getId());
        }
        return message;
    }
}
