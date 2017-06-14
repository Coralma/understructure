package com.cccis.base.elasticsearch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cccis.base.elasticsearch.operation.IndexOperation;
import com.cccis.base.elasticsearch.operation.MappingBuilder;

/**
 * for ClaimTask's index
 * Created by dongwt on 2016/9/23.
 */
public class ClaimTaskIndexOperation {
    IndexOperation indexOperation;

    private String indexName;

    private String typeName;

    @Before
    public void before() {
        this.indexOperation = new IndexOperation();
        //dev
        //        this.indexName = "devclaim";
        //        this.typeName = "devclaimtask";

        //19 test
        //        this.indexName = "claim";
        //        this.typeName = "claimtask";

        //performance
        //        this.indexName = "claimperformance";
        //        this.typeName = "claimtask";

//        this.indexName = "mytestclaim";
//        this.typeName = "claimtask";
    }

   // @Test
    public void execute() {
        MappingBuilder mappingFields = MappingBuilder.getInstance();
        mappingFields.addField("claimId", "long");
        mappingFields.addFieldUsingLike("warning", "string");
        mappingFields.addFieldUsingLike("accidentNo", "string");
        mappingFields.addFieldUsingLike("reportNo", "string");
        mappingFields.addFieldUsingLike("compulsoryPolicyNo", "string");
        mappingFields.addFieldUsingLike("commercialPolicyNo", "string");
        mappingFields.addFieldUsingLike("policyNo", "string");
        mappingFields.addFieldUsingLike("plateNo", "string");
        mappingFields.addFieldUsingLike("claimNo", "string");
        mappingFields.addField("downLoadCount", "integer");
        mappingFields.addField("claimCompanyId", "long");
        mappingFields.addFieldUsingLike("claimCompanyName", "string");
        mappingFields.addFieldUsingLike("claimGroupName", "string");
        mappingFields.addField("operableCompanyId", "long");
        mappingFields.addField("companyId", "long");
        mappingFields.addFieldUsingLike("vehicleManufMakeName", "string");
        mappingFields.addFieldUsingLike("vehicleSeriesName", "string");
        mappingFields.addFieldUsingLike("vehicleMakeAndSeriesName", "string");
        mappingFields.addFieldUsingLike("lossVehicle", "string");
        mappingFields.addFieldUsingLike("claimStatusDesc", "string");
        mappingFields.addFieldUsingLike("claimStatus", "string");
        mappingFields.addField("totalEstimateAmount", "double");
        mappingFields.addFieldUsingLike("insuredName", "string");
        mappingFields.addField("reportDate", "date");
        mappingFields.addField("assignDate", "date");
        mappingFields.addField("firstAssignDate", "date");
        mappingFields.addField("enterDate", "date");
        mappingFields.addFieldUsingLike("signTip", "string");
        mappingFields.addField("warningTime", "double");
        mappingFields.addFieldUsingLike("warningTimeInFormat", "string");
        mappingFields.addFieldUsingLike("taskNo", "string");
        mappingFields.addField("resurveyDate", "date");
        mappingFields.addFieldUsingLike("vehicleSeries", "string");
        mappingFields.addFieldUsingLike("custormerType", "string");
        mappingFields.addFieldUsingLike("customerLevel", "string");
        mappingFields.addFieldUsingLike("claimSource", "string");
        mappingFields.addFieldUsingLike("fastClaim", "string");
        mappingFields.addFieldUsingLike("appraiser", "string");
        mappingFields.addField("estimatorUserId", "long");
        mappingFields.addFieldUsingLike("reinspector", "string");
        mappingFields.addFieldUsingLike("priceChecker", "string");
        mappingFields.addFieldUsingLike("resurveyChecker", "string");
        mappingFields.addFieldUsingLike("taskType", "string");
        mappingFields.addField("startDate", "date");
        mappingFields.addField("auditScore", "long");
        mappingFields.addFieldUsingLike("bigCaseFlag", "string");
        mappingFields.addFieldUsingLike("nodeType", "string");
        mappingFields.addField("singleNodeWarningCfg", "double");
        mappingFields.addField("compoundNodeWarningCfg", "double");
        mappingFields.addField("userId", "long");
        mappingFields.addFieldUsingLike("userType", "string");
        mappingFields.addFieldUsingLike("transferAction", "string");
        mappingFields.addFieldUsingLike("claimCheckOut", "string");
        mappingFields.addFieldUsingLike("entrustFlag", "string");
        mappingFields.addField("estimateCompanyId", "long");
        mappingFields.addFieldUsingLike("estimateCompanyName", "string");
        mappingFields.addFieldUsingLike("insuranceName", "string");
        mappingFields.addField("version", "long");
        mappingFields.addFieldUsingLike("taskPoolFlag", "string");
        mappingFields.addFieldUsingLike("repairStatus", "string");
        mappingFields.addFieldUsingLike("vin", "string");
        mappingFields.addFieldUsingLike("repairStatusDesc", "string");
        mappingFields.addField("insuranceId", "long");
        mappingFields.addField("warningCompareData", "double");
        mappingFields.addField("allEstimateConsumedTime", "double");
        mappingFields.addField("warningCompareDataForEst", "double");
        mappingFields.addField("nodeConsumedTimeForEst", "double");
        mappingFields.addFieldUsingLike("userIdStr", "string");
        boolean result = this.indexOperation.createIndex(this.indexName, this.typeName, mappingFields);
        Assert.assertTrue(result);
    }

    //    @Test
    public void testDeleteIndex() {
        boolean result = this.indexOperation.deleteIndex(this.indexName);
        Assert.assertTrue(result);
    }

    @Test
    public void test() {
        Assert.assertTrue(Boolean.TRUE);
    }
}
