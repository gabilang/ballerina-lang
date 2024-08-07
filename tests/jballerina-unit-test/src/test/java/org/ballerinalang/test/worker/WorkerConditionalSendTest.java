/*
 *  Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com)
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.ballerinalang.test.worker;

import org.ballerinalang.test.BCompileUtil;
import org.ballerinalang.test.BRunUtil;
import org.ballerinalang.test.CompileResult;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Worker conditional send related tests.
 *
 * @since 2201.9.0
 */
public class WorkerConditionalSendTest {

    private CompileResult result;

    @BeforeClass
    public void setup() {
        this.result = BCompileUtil.compile("test-src/workers/workers_conditional_send.bal");
        Assert.assertEquals(result.getErrorCount(), 0);
    }

    @Test(dataProvider = "functionProvider")
    public void workerConditionalSendTest(String funcName) {
        BRunUtil.invoke(result, funcName, new Object[0]);
    }

    @DataProvider
    public static String[] functionProvider() {
        return new String[] {
                "workerConditionalSendTest",
                "sameWorkerSendTest",
                "sameWorkerSendEitherOnePath",
                "sameWorkerSendAltReceiveSendError",
                "sameWorkerSendAltReceiveReceiverError",
                "sameWorkerSendElse",
                "sameWorkerSendSenderPanic",
                "sameWorkerSendReceiverPanic",
                "sameWorkerSendMultiplePath1",
                "sameWorkerSendMultiplePath2",
                "sameWorkerSendMultiplePathError1",
                "sameWorkerSendMultiplePathError2",
                "sameWorkerSendMultiplePathError3",
                "sameWorkerSendMultiplePathError4",
                "multipleReceiveConditional",
                "multipleReceiveWithNonConditionalSend",
                "testNonTopLevelSend",
                "testSendWithEarlyReturnError",
                "testWorkerEarlyReturnWithinIf"
        };
    }

    @AfterClass
    public void tearDown() {
        result = null;
    }
}
