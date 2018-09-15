package uriparser;  // DO NOT CHANGE THIS OR YOU WILL GET ZERO

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * SOFTENG 254 2018 Assignment 1 submission
 *
 * Author: (Chuyang Chen, cche381)
 **/

public class TestURIParser {// DO NOT CHANGE THE CLASS NAME OR YOU WILL GET ZERO

    // a method to make URIs with the correct format, as long as a string input is in every parameter
    private URI URIMaker(String scheme, String authority, String path, String query, String fragment) {
        String uriString = scheme + ":" + "//"+ authority + path + "?" + query + "#" + fragment;
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse(uriString);
        return uri;
    }

    //----------------BASIC TESTS------------------//

    @Test
    //Test if 'getScheme' works as intended
    public void testGetScheme() {
        URI uri = URIMaker("http", "www.cs.auckland.ac.nz", "/references/", "item=199", "java");
        assertEquals(uri.getScheme(), "http");
    }

    @Test
    //Test if 'getAuthority' works as intended
    public void testGetAuthority() {
        URI uri = URIMaker("http", "www.cs.auckland.ac.nz", "/references/", "item=199", "java");
        assertEquals(uri.getAuthority(), "www.cs.auckland.ac.nz");
    }

    @Test
    //Test if 'getPath' works as intended
    public void testGetPath() {
        URI uri = URIMaker("http", "www.cs.auckland.ac.nz", "/references/", "item=199", "java");
        assertEquals(uri.getPath(), "/references/");
    }

    @Test
    //Test if 'getQuery' works as intended
    public void testGetQuery() {
        URI uri = URIMaker("http", "www.cs.auckland.ac.nz", "/references/", "item=199", "java");
        assertEquals(uri.getQuery(), "item=199");
    }

    @Test
    //Test if 'getFragment' works as intended
    public void testGetFragment() {
        URI uri = URIMaker("http", "www.cs.auckland.ac.nz", "/references/", "item=199", "java");
        assertEquals(uri.getFragment(), "java");
    }

    //-----------------CASE TESTS------------------//

    @Test
    /*Test the case if the input to the parser is null
    *
    * This tests if a ParseException is thrown like is required by the documentation, and if the correct
    * error message is provided in this special case.
     */
    public void testNullInputs() {
        URIParser uriparser = new URIParser();
        try {
            URI uri = uriparser.parse(null);
            fail("Should have generated a ParseException");
        } catch(ParseException e){
            assertEquals("Invalid null input", e.getMessage());
        }
    }

    @Test
    /* Test the case if the input is a string which is empty
     *
     * This allows us to see if the code still acts how its supposed to given an empty string as a special, one-off case
     */
    public void testEmptyStringInput(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* Test the case if the input is a string with no URI formatting (does not use any of the URI punctuation)
     *
     * This is worth testing since it is a special case which may occur due if the user forgets to format their URI
     * whatsoever
     */
    public void testLetters(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("abcdefg");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "abcdefg");
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* Test for correct formatting using punctuation but empty strings
    *
    * This allows us to see if the code still acts how its supposed to given nothing but punctuation in the correct
    * format as a special, one-off case
    */
    public void testEmptyInputsPlusPunctuation(){
        URI uri = URIMaker("", "", "", "", "");

        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "://");
        assertEquals(uri.getQuery(), "");
        assertEquals(uri.getFragment(), "");
    }

    //___________________________________________________________________________________________
    //THE FOLLOWING TESTS WILL COVER EVERY POSSIBLE COMBINATION OF THE 'PARTS' OF THE URI
    /*TESTS FOR DIFFERENT COMBINATIONS OF SCHEME AND OTHER PART*/

    @Test
    /* test for a URI with only a scheme and nothing else
    *
    * This allows failure detection to see if code can handle scheme-only inputs as a special, one-off case
     */
    public void testScheme1(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("a:");
        assertEquals(uri.getScheme(), "a");
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a scheme and authority together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the scheme and authority
     * together.
     *
     * Other parts of the URI that come after (ie. fragment, query, path) may still exist in combination with this case.
     */
    public void testScheme2(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("a://b");
        assertEquals(uri.getScheme(), "a");
        assertEquals(uri.getAuthority(), "b");
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a scheme and path together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the scheme and path
     * together, with nothing in between (ie. no authority)
     *
     * Other parts of the URI that come after (ie. fragment, query) may still exist in combination with this case.
     */
    public void testScheme3(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("a:b");
        assertEquals(uri.getScheme(), "a");
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "b");
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a scheme and query together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the scheme and query
     * together, with nothing in between (ie. no path, authority).
     *
     * Other parts of the URI that come after (ie. fragment) may still exist in combination with this case.
     */
    public void testScheme4(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("a:?b");
        assertEquals(uri.getScheme(), "a");
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), "b");
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a scheme and fragment together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the scheme and fragment
     * together, with nothing in between (ie. no path, query, authority).
     */
    public void testScheme5(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("a:#b");
        assertEquals(uri.getScheme(), "a");
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), "b");
    }

    //_____________________________________________________________________
    /*TESTS FOR DIFFERENT COMBINATIONS OF AUTHORITY AND OTHER PART*/

    @Test
    /* test for a URI with only an authority which is an empty string
     *
     * This allows failure detection to see if code can correctly handle an empty authority uri with nothing else as a
     * one-off special case
     */
    public void testAuthority1(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("//");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), "");
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only an authority
     *
     * This allows failure detection to see if code can correctly handle an authority-only uri with nothing else as a
     * one-off special case
     */
    public void testAuthority2(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("//a");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), "a");
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a authority and query together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the authority and query
     * together, with nothing that comes prior (ie. no scheme).
     *
     * Other parts of the URI that come after (ie. fragment, query) may still exist in combination with this case.
     */
    public void testAuthority3(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("//a/b/");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), "a");
        assertEquals(uri.getPath(), "/b/");
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a authority and query together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the authority and query
     * together, with nothing in between (ie. no path) and nothing that comes prior (ie. no scheme).
     *
     * Other parts of the URI that come after (ie. fragment) may still exist in combination with this case.
     */
    public void testAuthority4(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("//a?b");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), "a");
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), "b");
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a authority and fragment together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the authority and fragment
     * together,  with nothing in between (ie. no path, query) and nothing that comes prior (ie. no scheme).
     */
    public void testAuthority5(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("//a#b");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), "a");
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), "b");
    }

    //_____________________________________________________
    /*TESTS FOR DIFFERENT COMBINATIONS OF PATH AND OTHER PART*/
    @Test
    /* test for a URI with only an path which is a "/" string
     *
     * This allows failure detection to see if code can correctly handle an this input URI with nothing else as a
     * one-off special case
     */
    public void testPath1(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("/");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "/");
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only an path which is a "/a" string
     *
     * This allows failure detection to see if code can correctly handle an this input URI with nothing else as a
     * one-off special case
     */
    public void testPath2(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("/a");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "/a");
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a path and query together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the path and query
     * together, with nothing that comes prior (ie. no scheme, authority).
     *
     * Other parts of the URI that come after (ie. fragment) may still exist in combination with this case.
     */
    public void testPath3(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("/a?b");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "/a");
        assertEquals(uri.getQuery(), "b");
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a path and fragment together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the path and fragment
     * together,  with nothing in between (ie. no query) and nothing that comes prior (ie. no scheme, authority).
     */
    public void testPath4(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("/a#b");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "/a");
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), "b");
    }

    //______________________________________________________
    /*TESTS FOR DIFFERENT COMBINATIONS OF QUERY AND OTHER PART*/

    @Test
    /* test for a URI with only an query which is an empty string
     *
     * This allows failure detection to see if code can correctly handle an empty query uri with nothing else as a
     * one-off special case
     */
    public void testQuery1(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("?");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), "");
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only an query string
     *
     * This allows failure detection to see if code can correctly handle an query-only uri with nothing else as a
     * one-off special case
     */
    public void testQuery2(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("?a");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), "a");
        assertEquals(uri.getFragment(), null);
    }

    @Test
    /* test for a URI with only a query and fragment together
     *
     * This allows failure detection to see if code can correctly handle any combination of only the query and fragment
     * together, with nothing that comes prior (ie. no scheme, authority, path).
     */
    public void testQuery3(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("?a#b");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), "a");
        assertEquals(uri.getFragment(), "b");
    }

    //_______________________________________________________
    /*TEST FOR FRAGMENT*/

    @Test
    /* test for a URI with only an fragment which is an empty string
     *
     * This allows failure detection to see if code can correctly handle an empty fragment uri with nothing else as a
     * one-off special case
     */
    public void testFragment1(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("#");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), "");
    }


    @Test
    /* test for a URI with only an fragment string
     *
     * This allows failure detection to see if code can correctly handle an fragment-only uri with nothing else as a
     * one-off special case
     */
    public void testFragment2(){
        URIParser uriparser = new URIParser();
        URI uri = uriparser.parse("#a");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), null);
        assertEquals(uri.getFragment(), "a");
    }

    //________________________________________________________
    /*THE FOLLOWING TESTS ALL TEST FOR A SINGLE URI PART INPUT WITH FORMATTING FOR THE ENTIRE URI STRING*/

    @Test
    /* test for when a string is only present in scheme, while all other parts of uri have empty strings
     * but has the correct format for URI:
     *
     * This allows failure detection to see if the code acts as expected given only a scheme and formatting as a
     * special case
     * */
    public void test01(){
        URI uri = URIMaker("a", "", "", "", "");
        assertEquals(uri.getScheme(), "a");
        assertEquals(uri.getAuthority(), "");
        assertEquals(uri.getPath(), null);
        assertEquals(uri.getQuery(), "");
        assertEquals(uri.getFragment(), "");
    }

    @Test
    /* test for when a string is only present in Authority, while all other parts of uri have empty strings
     * but has the correct format for URI:
     *
     * This allows failure detection to see if the code acts as expected given only an authority and formatting as a
     * special case
     * */
    public void test02(){
        URI uri = URIMaker("", "b", "", "", "");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "://b");
        assertEquals(uri.getQuery(), "");
        assertEquals(uri.getFragment(), "");
    }

    @Test
    /* test for when a string is only present in Path, while all other parts of uri have empty strings
     * but has the correct format for URI:
     *
     * This allows failure detection to see if the code acts as expected given only a path and formatting as a
     * special case
     * */
    public void test03(){
        URI uri = URIMaker("", "", "c", "", "");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "://c");
        assertEquals(uri.getQuery(), "");
        assertEquals(uri.getFragment(), "");
    }

    @Test
    /* test for when a string is only present in Query, while all other parts of uri have empty strings
     * but has the correct format for URI:
     *
     * This allows failure detection to see if the code acts as expected given only a query and formatting as a
     * special case
     * */
    public void test04(){
        URI uri = URIMaker("", "", "", "d", "");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "://");
        assertEquals(uri.getQuery(), "d");
        assertEquals(uri.getFragment(), "");
    }

    @Test
    /* test for when a string is only present in Fragment, while all other parts of uri have empty strings
     * but has the correct format for URI:
     *
     * This allows failure detection to see if the code acts as expected given only a fragment and formatting as a
     * special case
     * */
    public void test05(){
        URI uri = URIMaker("", "", "", "", "e");
        assertEquals(uri.getScheme(), null);
        assertEquals(uri.getAuthority(), null);
        assertEquals(uri.getPath(), "://");
        assertEquals(uri.getQuery(), "");
        assertEquals(uri.getFragment(), "e");
    }
    //-------------------------
}
