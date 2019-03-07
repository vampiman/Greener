package server;

import cn.hutool.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocalProduceTest {

    private LocalProduce lp;
    private JSONObject joCor; //correct JSON object for response testing
    private JSONObject joIncor; //incorrect JSON object for response testing


    /**
     * Sets up all the variables
     * needed for testing before
     * the tests are executed.
     */
    @Before
    public void setup() {
        lp = new LocalProduce();

        joCor = new JSONObject();
        joCor.append("Weight", "100");
        joIncor = new JSONObject();
        joIncor.append("Weight", "200");
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects true.
     */
    @Test
    public void getDataCorrectEntity() {
        Assert.assertEquals(lp.getData().getEntity(),
                joCor);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in GET response body.
     * Expects false.
     */
    @Test
    public void getDataIncorrectEntity() {
        Assert.assertNotEquals(lp.getData().getEntity(),
                joIncor);
    }

    /**
     * Tests whether the GET method
     * returns a response with an
     * OK Status (200).
     * Expects true.
     */
    @Test
    public void getDataCorrectStatus() {
        Assert.assertEquals(lp.getData().getStatus(), 200);
    }

    /**
     * Tests whether the GET method
     * returns a response with an
     * OK Status (200).
     * Expects false.
     */
    @Test
    public void getDataIncorrectStatus() {
        Assert.assertNotEquals(lp.getData().getStatus(), 404);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in POST response body.
     * Expects true.
     */
    @Test
    public void postDataCorrectEntity() {
        Assert.assertEquals(lp.postData(joCor).getEntity(),
                joCor);
    }

    /**
     * Tests the equality between an arbitrary
     * JSON value equal to the pre-set
     * value in POST response body.
     * Expects false.
     */
    @Test
    public void postDataIncorrectEntity() {
        Assert.assertNotEquals(lp.postData(joCor).getEntity(),
                joIncor);
    }

    /**
     * Tests whether the POST method
     * returns a response with an
     * OK Status (200).
     * Expects true.
     */
    @Test
    public void postDataCorrectStatus() {
        Assert.assertEquals(lp.postData(joCor).getStatus(),
                200);
    }

    /**
     * Tests whether the POST method
     * returns a response with an
     * OK Status (200).
     * Expects false.
     */
    @Test
    public void postDataIncorrectStatus() {
        Assert.assertNotEquals(lp.postData(joCor).getStatus(),
                404);
    }
}