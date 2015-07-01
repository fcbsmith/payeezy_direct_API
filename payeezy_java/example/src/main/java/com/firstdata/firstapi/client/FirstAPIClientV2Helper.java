package com.firstdata.firstapi.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.util.CharsetUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.DeserializationConfig;
//import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.events.Event;

import com.firstdata.firstapi.client.domain.TransactionType;
import com.firstdata.firstapi.client.domain.v2.Card;
import com.firstdata.firstapi.client.domain.v2.Token;
import com.firstdata.firstapi.client.domain.v2.TransactionRequest;
import com.firstdata.firstapi.client.domain.v2.TransactionResponse;
import com.firstdata.firstapi.client.domain.v2.Transarmor;


public class FirstAPIClientV2Helper {
    
    private static final Logger log=LoggerFactory.getLogger(FirstAPIClientV2Helper.class);
    
    @Autowired
    RestTemplate restTemplate;
    
    private String url = URL_VALUE;
    private String appId = APIKEY_VALUE;
    private String securedSecret = APISECRET_VALUE;
    private String token = TOKEN_VALUE;
    private String merchantid = MERCHANTID_VALUE;
    
	boolean initiated = false;
    private String trToken = TRTOKEN_VALUE;
    private String urltoken = "";
    private String ta_token = TA_TOKEN_VALUE;
    
    public FirstAPIClientV2Helper() {
		
    	/*
    	// TODO Auto-generated constructor stub
    	org.springframework.http.converter.json.MappingJacksonHttpMessageConverter converter = new org.springframework.http.converter.json.MappingJacksonHttpMessageConverter();
    	converter.getObjectMapper().configure(Feature.WRITE_NULL_MAP_VALUES, false);
    	converter.getObjectMapper().configure(Feature.WRITE_NULL_PROPERTIES, false);
    	converter.getObjectMapper().configure(Feature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);
    	
    	
    	converter.getObjectMapper().configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
    	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.INTERN_FIELD_NAMES, true);
    	
    	
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
    	
    	//converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.AUTO_DETECT_FIELDS, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.AUTO_DETECT_CREATORS, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.WRAP_ROOT_VALUE, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.USE_ANNOTATIONS, false);
    	converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.WRAP_EXCEPTIONS, false);
    	
    	//converter.getObjectMapper().configure( JsonSerialize.Inclusion.NON_NULL);
    	converter.getObjectMapper().getSerializationConfig().setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
    	*/
    	
    	this.setAppId(APIKEY_VALUE);
    	this.setSecuredSecret(APISECRET_VALUE);
    	this.setToken(TOKEN_VALUE);
    	this.setMerchantid(MERCHANTID_VALUE);
    	this.setTrToken(TRTOKEN_VALUE);
    	this.setUrl(URL_VALUE);
    	this.setTa_token(TA_TOKEN_VALUE);
    	//OVERRIDE= "overrides";
    	OVERRIDE= "override";
    	
	}
    
    
    public String getUrl() {
    	
        return url;
    }

    
    public void setUrl(String url) {
    	if(OVERRIDE == "override")
    	{
    		this.url = url;
    	}
    }

    
    public String getAppId() {
        return appId;
    }

    
    public void setAppId(String appId) {
    	if(OVERRIDE == "override")
    	{
    		this.appId = appId;
    	}
    }

    
    public String getSecuredSecret() {
        return securedSecret;
    }

    
    public void setSecuredSecret(String secureId) {
    	if(OVERRIDE == "override")
    	{
    		this.securedSecret = secureId;
    	}
    }
        
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
    	if(OVERRIDE == "override")
    	{
    		this.token = token;
    	}
    }
    
    public String getTrToken() {
        return trToken;
    }
    
    public void setTrToken(String trtoken) {
    	if(OVERRIDE == "override")
    	{
    		this.trToken = trtoken;
    	}
    }

    public String getMerchantid() {
		return merchantid;
	}


	public void setMerchantid(String merchantid) {
		if(OVERRIDE == "override")
    	{
			this.merchantid = merchantid;
    	}
	}


    public String getTa_token() {
		return ta_token;
	}


	public void setTa_token(String ta_token) {
		if(OVERRIDE == "override")
    	{
			this.ta_token = ta_token;
    	}
	}



/*    private String getValue(String value){
        return new StringBuilder().append("\"").append(value).append("\"").toString();
    }
    */
    
	public static final String APIKEY_VALUE="C5VAeX3WwStH0ZDnlF4eXVhHJiIedDtY";
    public static final String APISECRET_VALUE="2b940ece234ee38131e70cc617aa2afa3d7ff8508856917958e7feb3ef190447";
    public static final String TOKEN_VALUE="fdoa-a480ce8951daa73262734cf102641994c1e55e7cdf4c02b6";
    
    public static final String MERCHANTID_VALUE="OGEzNGU3NjM0ODQyMTU3NzAxNDg0MjE4NDY4ZTAwMDA=";
    public static final String TRTOKEN_VALUE="y6pzAbc3Def123";
    public static final String TA_TOKEN_VALUE="NOIW";
    public static final String URL_VALUE="https://api-int.payeezy.com/v1";
    public static String OVERRIDE="override";
    
    
    private static final String NONCE="nonce";
    public static final String APIKEY="apikey";
    public static final String APISECRET="pzsecret";
    public static final String TOKEN="token";
    public static final String TIMESTAMP="timestamp";
    public static final String AUTHORIZE="Authorization";
    public static final String PAYLOAD="payload";
    public static final String MERCHANTID="merchantid";
    public static final String TRTOKEN="trtoken";
    public static final String TA_TOKEN="ta_token";
    public static final String URL="https://api-int.payeezy.com/v1";
    //public static String OVERRIDE="override";
    
    
	private Map<String,String> getSecurityKeys(String appId,String secureId,String payLoad) throws Exception{

        Map<String,String> returnMap=new HashMap<String,String>();
        long nonce;
        try {
            
            nonce = Math.abs(SecureRandom.getInstance ("SHA1PRNG").nextLong());     
            log.debug("SecureRandom nonce:{}",nonce);
            returnMap.put(NONCE, Long.toString(nonce));
            returnMap.put(APIKEY, this.appId);
            returnMap.put(TIMESTAMP, Long.toString(System.currentTimeMillis()));
            returnMap.put(TOKEN, this.token);
            returnMap.put(APISECRET, this.securedSecret);
            //payLoad = payLoad.replace("\"credit_card\"", "credit_card");
            //payLoad = payLoad.replace( new String("credit_card").subSequence(0, 11), new String("\"credit_card\"").subSequence(0, 13) ); 
            returnMap.put(PAYLOAD, payLoad);
            returnMap.put(AUTHORIZE, getMacValue(returnMap));
            return returnMap;
            
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }       
    }
    
    public String getMacValue(Map<String,String> data) throws Exception{
        Mac mac=Mac.getInstance("HmacSHA256");
        String apiSecret= data.get(APISECRET);
        log.debug("API_SECRET:{}",apiSecret);
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        mac.init(secret_key);
        StringBuilder buff=new StringBuilder();
        buff.append(data.get(APIKEY))
        .append(data.get(NONCE))
        .append(data.get(TIMESTAMP));
        if(data.get(TOKEN)!=null)
            buff.append(data.get(TOKEN));
        if(data.get(PAYLOAD)!=null)
            buff.append(data.get(PAYLOAD));

        log.info(buff.toString());
        byte[] macHash=mac.doFinal(buff.toString().getBytes("UTF-8"));
        log.info("MacHAsh:{}",Arrays.toString( macHash));
        String authorizeString=Base64.encodeBase64String(toHex(macHash));
        log.info("Authorize: {}",authorizeString);
        return authorizeString;
    
    }
    
    public void Initialize()
    {
    	if(!initiated)
    	{
    		//org.springframework.http.converter.xml.MarshallingHttpMessageConverter mconverter = new MarshallingHttpMessageConverter();
    		//restTemplate.getMessageConverters().add(mconverter);
    		
    		org.springframework.http.converter.StringHttpMessageConverter sconverter = new StringHttpMessageConverter();
    		restTemplate.getMessageConverters().add(sconverter);
    		
    		
    		org.springframework.http.converter.json.MappingJacksonHttpMessageConverter converter = new org.springframework.http.converter.json.MappingJacksonHttpMessageConverter();
        	converter.getObjectMapper().configure(Feature.WRITE_NULL_MAP_VALUES, false);
        	converter.getObjectMapper().configure(Feature.WRITE_NULL_PROPERTIES, false);
        	converter.getObjectMapper().configure(Feature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);
        	/*
        	converter.getObjectMapper().configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        	//converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
        	converter.getObjectMapper().configure(org.codehaus.jackson.JsonParser.Feature.INTERN_FIELD_NAMES, true);
        	
        	
        	converter.getObjectMapper().configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        	*/
        	
        	/*
        	//converter.getObjectMapper().configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.AUTO_DETECT_CREATORS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.WRAP_ROOT_VALUE, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.WRAP_EXCEPTIONS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS, false);
        	converter.getObjectMapper().configure(DeserializationConfig.Feature.USE_GETTERS_AS_SETTERS, false);
        	*/
        	
        	
        	
        	
        	//converter.getObjectMapper().configure( JsonSerialize.Inclusion.NON_NULL);
        	converter.getObjectMapper().getSerializationConfig().setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
        	
        	//converter.getObjectMapper().getSerializationConfig().configure(SerializationConfig.Feature.STRING_PASS_THROUGH, true);
        	converter.getObjectMapper().getSerializationConfig().set(SerializationConfig.Feature.USE_ANNOTATIONS, true);
        	
        	converter.getObjectMapper().getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.USE_ANNOTATIONS, true);
        	converter.getObjectMapper().getDeserializationConfig().set(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, true);
        	
        	
        	
        	List<MediaType> mediatypes=new ArrayList<MediaType>();
        	mediatypes.add(MediaType.APPLICATION_XML);
        	mediatypes.add(MediaType.APPLICATION_JSON);
        	mediatypes.add(MediaType.ALL);
        	converter.setSupportedMediaTypes(mediatypes);
        	restTemplate.getMessageConverters().add(converter);
        	
        	initiated = true;
    	}
    }
    
    public byte[] toHex(byte[] arr) {
        String hex=Hex.encodeHexString(arr);
        log.info("Apache common value:{}" , hex);
        return hex.getBytes();
    }
    private HttpHeaders getHttpHeader(String appId,String securityKey,String payload ) throws Exception{
        Initialize();
    	Map<String,String> encriptedKey=getSecurityKeys(appId, securityKey,payload);
        HttpHeaders header=new HttpHeaders();
        Iterator<String> iter=encriptedKey.keySet().iterator();
        while(iter.hasNext()) {
            String key=iter.next();
            if(PAYLOAD.equals(key))
                continue;
            header.add(key, encriptedKey.get(key));
        }
        //header.add("contentType", "application/json; charset=UTF-8");
        //header.add("Accept", "*/*");
        header.add("Accept", "application/json");
        //header.add("Accept", "text/plain");
        //header.setContentType(MediaType.APPLICATION_JSON);
        header.setContentType(MediaType.APPLICATION_JSON);
		//if(this.url.contains("?"))
		//{
			//header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		//}
		//else
		//{
			List<MediaType> mediatypes = new ArrayList<MediaType>();
			mediatypes.add(MediaType.APPLICATION_JSON);
			//get
			//mediatypes.add(MediaType.TEXT_PLAIN);
			mediatypes.add(new MediaType("application", "json", Charset.forName("UTF-8")));
			//mediatypes.add(new MediaType("text", "plain", Charset.forName("UTF-8")));
			//header.setAccept(mediatypes);
		//}
		// For get call accept mediatype MediaType.APPLICATION_JSON_VALUE
        //header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        //header.add("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        //header.add("User-Agent", "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
        //header.add("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		header.add("User-Agent", "Java/1.6.0_26");
        //header.add("trtoken", this.trtoken);
        //header.add("trtoken", this.trtoken);
		//merchant id required for get
        //header.add("x-merchant-identifier", "OGEzNGU3NjM0ODQyMTU3NzAxNDg0MjE4NDY4ZTAwMDA=");
		//header.add("x-merchant-identifier", this.merchantid);
        
        return header;
    }
    public String getJSONObject(Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.getSerializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature. , state)SerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(Feature.WRITE_NULL_PROPERTIES, false);
        objectMapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        objectMapper.getSerializationConfig().setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        OutputStream stream = new BufferedOutputStream(byteStream);
        JsonGenerator jsonGenerator =
            objectMapper.getJsonFactory().createJsonGenerator(stream,
                                                              JsonEncoding.UTF8);
        
        // mapper.getSerializationConfig();
        objectMapper.writeValue(jsonGenerator, data);
        // mapper.writeValue(stream, payload);
        stream.flush();
        return new String(byteStream.toByteArray());
    }
    public TransactionResponse doPrimaryTransaction(TransactionRequest trans) throws Exception{
    	
        String url=this.url+"/transactions";
        //if( ( trans.getToken() == null) && ( trans.getType() == "FDToken") && ( trans.getTa_token() == "NOIW") && ( trans.getAuth() == "false"))
        if( ( trans.getToken() == null) && ( trans.getType() == "FDToken") && ( trans.getTa_token() == TA_TOKEN_VALUE) && ( trans.getAuth() == "false"))
        {
        	//url=this.url+"/securitytokens";
        	url=this.url+"/transactions/tokens";
        }
        this.urltoken = url;
        if(!(url.endsWith("tokens")))
        {
            Assert.notNull(trans.getAmount(),"Amount is not present");
	        Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
        }
        
        String payload=getJSONObject(trans);
        //payload = payload.replace("\"credit_card\"", "credit_card");
        HttpEntity<TransactionRequest> request=new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
        ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.POST, request, TransactionResponse.class);
//        ResponseEntity<HashMap> response= restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class);
//      return doTransaction(trans,credentials);
        return response.getBody();
//        return null;
    
    }
       public TransactionResponse doSecondaryTransaction(TransactionRequest trans) throws Exception{
            Assert.notNull(trans.getTransactionTag(),"Transaction Tag is not present");
            Assert.notNull(trans.getId(),"Id is not present");    
            Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
            String url=this.url+"/transactions/{id}";
            String payload=getJSONObject(trans);
            HttpEntity<TransactionRequest> request=new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
            ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.POST, request, TransactionResponse.class,trans.getId());
            //return doTransaction(trans,credentials);
            return response.getBody();
            //return null;
        }
       
	    public TransactionResponse doPrimaryTransactionObject(TransactionRequest trans) throws Exception{
	    	    
	       	   String url=this.url+"/transactions";
	           //if( ( trans.getToken() == null) || ( trans.getType() == "FDToken") || ( trans.getToken().getTokenData().getValue() == "") || ( trans.getToken().getTokenData().getValue() == "NOIW"))
	           if( ( trans.getToken() == null) || ( trans.getType() == "FDToken") || ( trans.getToken().getTokenData().getValue() == "") || ( trans.getToken().getTokenData().getValue() == TA_TOKEN_VALUE))
	           {
		           	url=this.url+"/transactions/tokens";
	           }
	           this.urltoken = url;
	           if(!(url.endsWith("tokens")))
	           {
	               Assert.notNull(trans.getAmount(),"Amount is not present");
	   	           Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
	           }
	           
	           String payload=getJSONObject(trans);
	           HttpEntity<TransactionRequest> request=new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
	           //ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.POST, request, TransactionResponse.class);
	           ResponseEntity<Object> response= restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
	           //ResponseEntity<HashMap> response= restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class);
	           //return doTransaction(trans,credentials);
	           Object o2 = response.getBody();
	           TransactionResponse resp = GetTransactionResponse(o2.toString());
	           //return (TransactionResponse) response.getBody();
	           return resp;
	//         return null;
	       
	      }
	      public TransactionResponse doSecondaryTransactionObject(TransactionRequest trans) throws Exception {
               Assert.notNull(trans.getTransactionTag(),"Transaction Tag is not present");
               Assert.notNull(trans.getId(),"Id is not present");    
               Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
               String url=this.url+"/transactions/{id}";
               trans.setTransactionType(trans.getTransactionType().toLowerCase());
               String payload=getJSONObject(trans);
               HttpEntity<TransactionRequest> request=new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
               //ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.POST, request, TransactionResponse.class,trans.getId());
               ResponseEntity<Object> response= restTemplate.exchange(url, HttpMethod.POST, request, Object.class, trans.getId());
  //           return doTransaction(trans,credentials);
               
               Object o2 = response.getBody();
	           TransactionResponse resp = GetTransactionResponse(o2.toString());
               return resp;
               //return (TransactionResponse)response.getBody();
  //           return null;
           }

          
       public TransactionResponse doPrimaryTokenTransaction(TransactionRequest trans) throws Exception{
           
           Assert.notNull(trans.getAmount(),"Amount is not present");
           Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
       
           String url=this.url+"/transactions/tokens";
           
           //if( ( trans.getToken().getTokenData().getValue() == "") || ( trans.getToken().getTokenData().getValue() == "NOIW"))
           if( ( trans.getToken().getTokenData().getValue() == "") || ( trans.getToken().getTokenData().getValue() == TA_TOKEN_VALUE))	   
           {
           		//url=this.url+"/securitytokens";
        	    url=this.url+"/transactions/tokens";
           }
           
           String payload=getJSONObject(trans);
           HttpEntity<TransactionRequest> request=new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
           ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.POST, request, TransactionResponse.class);
//           ResponseEntity<HashMap> response= restTemplate.exchange(url, HttpMethod.POST, request, HashMap.class);
//         return doTransaction(trans,credentials);
           return response.getBody();
//           return null;
       
       }
       
       public TransactionResponse doSecondaryTokenTransaction(TransactionRequest trans) throws Exception{
           Assert.notNull(trans.getTransactionTag(),"Transaction Tag is not present");
           Assert.notNull(trans.getId(),"Id is not present");    
           Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
           String url=this.url+"/transactions/{id}";
           String payload=getJSONObject(trans);
           HttpEntity<TransactionRequest> request=new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
           ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.POST, request, TransactionResponse.class,trans.getId());
//       return doTransaction(trans,credentials);
           return response.getBody();

//          return null;
       }
       
        @SuppressWarnings("unused")
	   	private TransactionResponse doGetPrimaryTransaction(TransactionRequest trans) throws Exception
	   	{
	       if(trans.getPaymentMethod().toLowerCase() != "valuelink")
	       {
	   	        Assert.notNull(trans.getCard().getName(),"Card holder name is empty");
	   	        Assert.notNull(trans.getCard().getExpiryDt(),"Card Expiry date is not present");
	   	        Assert.notNull(trans.getCard().getNumber(),"Card number is not present");
	       }
           
           if(trans.getPaymentMethod().toLowerCase() == "valuelink")
	       {
	           	Assert.notNull(trans.getValuelink().getCardNumber(),"Value Link Card number is not present");
	       }
           
           Assert.notNull(trans.getAmount(),"Amount is not present");
           Assert.notNull(trans.getTransactionType(),"Transaction type is not present");
       
           //String url=this.url+"/securitytokens";
           String url=this.url+"/transactions/tokens";
           String payload=getJSONObject(trans);
           HttpEntity<TransactionRequest> request = new HttpEntity<TransactionRequest>(trans,getHttpHeader(this.appId, this.securedSecret,payload));
           
           //request.getHeaders().setUserAgent(System.getProperty("http.agent"));
           //GET /v1/securitytokens?apikey=y6pWAJNyJyjGv66IsVuWnklkKUPFbb0a
           //&trtoken=y6pzAbc3Def123
           //&callback=Payeezy.callback
           //&type=payeezy
           //&credit_card.type=visa
           //&credit_card.cardholder_name=John%20Smith
           //&credit_card.card_number=4788250000028291
           //&credit_card.exp_date=1030
           //&credit_card.cvv=123 HTTP/1.1
           
           //working get call
           //https://api-int.payeezy.com/v1/securitytokens?auth=false&ta_token=NOIW&apikey=fP0iYUx4oJ8LolKl2LiOT1Zo94mL0IDQ&trtoken=y6pzAbc3Def123&callback=Payeezy.callback&type=FDToken&credit_card.type=American%20Express&credit_card.cardholder_name=xyz&credit_card.card_number=373953192351004&credit_card.exp_date=0416&credit_card.cvv=1234&auth=false&ta_token=NOIW
           //String surl = "https://api-int.payeezy.com/v1/securitytokens?auth={auth1}&ta_token={tatoken2}&apikey={apikey3}&trtoken={trtoken4}&callback={callback5}&type={tokentype6}&credit_card.type={cardtype7}&credit_card.cardholder_name={cardholdername8}}&credit_card.card_number={cardnumber9}&credit_card.exp_date={expiry10}&credit_card.cvv={cvv11}&auth={auth12}&ta_token={tatoken13}";
           //https://api-int.payeezy.com/v1/securitytokens?
           //auth=false
           //&ta_token=NOIW
           //&apikey=fP0iYUx4oJ8LolKl2LiOT1Zo94mL0IDQ
           //&trtoken=y6pzAbc3Def123
           //&callback=Payeezy.callback
           //&type=FDToken
           //&credit_card.type=American%20Express
           //&credit_card.cardholder_name=xyz
           //&credit_card.card_number=373953192351004
           //&credit_card.exp_date=0416
           //&credit_card.cvv=1234
           //&auth=false
           //&ta_token=NOIW
           URI uri = null;
           request.getBody().setCallback("Payeezy.callback");
           if(request.getBody().getToken().getTokenType() == "payeezy")
           {
        	   //url = url.replace("api-int", "api-cert");
	           UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
	        	        .queryParam("apikey", this.appId)
	        	        //.queryParam("trtoken", this.securedSecret)
	        	        .queryParam("trtoken", this.trToken)
	        	        .queryParam("callback", request.getBody().getCallback())
	        	        .queryParam("type", request.getBody().getToken().getTokenType())
	        	        .queryParam("credit_card.type", request.getBody().getToken().getTokenData().getType())
	        	        .queryParam("credit_card.cardholder_name", request.getBody().getToken().getTokenData().getName())
	        	        .queryParam("credit_card.card_number", request.getBody().getToken().getTokenData().getNumber())
	           			.queryParam("credit_card.exp_date", request.getBody().getToken().getTokenData().getExpiryDt())
	           			.queryParam("credit_card.cvv", request.getBody().getToken().getTokenData().getCvv());
	           url = builder.build().toUri().toString();
	           url = builder.build().toUri().toURL().toString();
           }
           else
           {
        	   //url = url.replace("api-cert", "api-int");
        	   url = url.replace("/transactions/tokens", "/securitytokens");
	           UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
	          	        .queryParam("auth", request.getBody().getAuth())
	          	        .queryParam("ta_token", request.getBody().getToken().getTokenData().getValue())
	          	        .queryParam("apikey", this.appId)
	          	        //.queryParam("trtoken", this.securedSecret)
	          	        .queryParam("trtoken", this.trToken)
	          	        .queryParam("callback", request.getBody().getCallback())
	          	        .queryParam("type", request.getBody().getToken().getTokenType())
	          	        .queryParam("credit_card.type", request.getBody().getToken().getTokenData().getType())
	          	        .queryParam("credit_card.cardholder_name", request.getBody().getToken().getTokenData().getName())
	          	        .queryParam("credit_card.card_number", request.getBody().getToken().getTokenData().getNumber())
	             		.queryParam("credit_card.exp_date", request.getBody().getToken().getTokenData().getExpiryDt())
	             		.queryParam("credit_card.cvv", request.getBody().getToken().getTokenData().getCvv())
	             		.queryParam("autha", request.getBody().getAuth())
	             		.queryParam("ta_tokena", request.getBody().getToken().getTokenData().getValue())
	             		;
	           uri = builder.build().toUri();
	             url = builder.build().toUri().toString();
	             url = builder.build().toUri().toURL().toString();
	             url =  url.replace("autha", "auth");
	             url =  url.replace("ta_tokena", "ta_token");
	             uri = new URI(url);
	             //url = url + "&auth=" + request.getBody().getAuth();
	             //url = url + "&ta_token=" + request.getBody().getToken().getTokenData().getValue();
           }
           
           String urlnew = this.url + "/securitytokens" + "?";
           urlnew = urlnew + "auth=false";
           urlnew = urlnew + "&ta_token=" + TA_TOKEN_VALUE;
           urlnew  = urlnew  + "&apikey=fP0iYUx4oJ8LolKl2LiOT1Zo94mL0IDQ";
           urlnew  = urlnew  + "&trtoken=y6pzAbc3Def123";
           urlnew  = urlnew  + "&callback=Payeezy.callback";
           urlnew  = urlnew  + "&type=FDToken";
           urlnew  = urlnew  + "&credit_card.type=American%20Express";
           urlnew  = urlnew  + "&credit_card.cardholder_name=xyz";
           urlnew  = urlnew  + "&credit_card.card_number=373953192351004";
           urlnew  = urlnew  + "&credit_card.exp_date=0416";
           urlnew  = urlnew  + "&credit_card.cvv=1234";
           urlnew  = urlnew  + "&auth=false";
           //urlnew  = urlnew  + "&ta_token=NOIW";
           urlnew  = urlnew  + "&ta_token=" + TA_TOKEN_VALUE;
           
           
           
           
           //request.getHeaders().add("x-merchant-identifier", this.merchantid);
           //request.getHeaders().add("x-merchant-identifier", "OGEzNGU3NjM0ODQyMTU3NzAxNDg0MjE4NDY4ZTAwMDA=");
           
           //Object objresponse= restTemplate.getForObject(uri, Object.class);
           String objresponse= restTemplate.getForObject(uri, String.class);
           
           
           //ResponseEntity<TransactionResponse> response= restTemplate.exchange(url, HttpMethod.GET, request, TransactionResponse.class);
           //ResponseEntity<Object> response= restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
           
           //ResponseEntity<Object> response= restTemplate.exchange("https://api-int.payeezy.com/v1/securitytokens?auth={auth1}&ta_token={tatoken2}&apikey={apikey3}&trtoken={trtoken4}&callback={callback5}&type={tokentype6}&credit_card.type={cardtype7}&credit_card.cardholder_name={cardholdername8}}&credit_card.card_number={cardnumber9}&credit_card.exp_date={expiry10}&credit_card.cvv={cvv11}&auth={auth12}&ta_token={tatoken13}", HttpMethod.GET, request, Object.class, "false", request.getBody().getTa_token(), this.appId, this.getTrToken(), "Payeezy.callback", request.getBody().getToken().getTokenType(), request.getBody().getToken().getTokenData().getType(), request.getBody().getToken().getTokenData().getName(), request.getBody().getToken().getTokenData().getNumber(), request.getBody().getToken().getTokenData().getExpiryDt(), request.getBody().getToken().getTokenData().getCvv(), "false", request.getBody().getTa_token() );
           //ResponseEntity<Object> response= restTemplate.exchange("https://api-int.payeezy.com/v1/securitytokens?auth=false&ta_token=NOIW&apikey=fP0iYUx4oJ8LolKl2LiOT1Zo94mL0IDQ&trtoken=y6pzAbc3Def123&callback=Payeezy.callback&type=FDToken&credit_card.type=American%20Express&credit_card.cardholder_name=xyz&credit_card.card_number=373953192351004&credit_card.exp_date=0416&credit_card.cvv=1234&auth=false&ta_token=NOIW", HttpMethod.GET, request, Object.class, "false" );
           Object response = objresponse;
           System.out.println(response.toString());
           String resString = response.toString();
           //String ro = response.getBody().toString();
           //TransactionResponse r = response.getBody(); 
           TransactionResponse r = GetTransactionResponse(response.toString()); 
           return r;
           
       }
           
	    public TransactionResponse purchaseTransaction(TransactionRequest trans) throws Exception {
	        
	        trans.setTransactionType(TransactionType.PURCHASE.name());
	        /*if( (trans.getToken() != null) && (trans.getToken().getTokenType() != null) && (trans.getToken().getTokenType().toUpperCase() == "FDTOKEN") )
	        {
	        	return doPrimaryTransactionObject(trans);
	        }*/
	        return doPrimaryTransaction(trans);
	    }
    
    public TransactionResponse authorizeTransaction(TransactionRequest trans) throws Exception{
        trans.setTransactionType(TransactionType.AUTHORIZE.name());
        if( (trans.getToken() != null) && (trans.getToken().getTokenType() != null) && (trans.getToken().getTokenType().toUpperCase() == "FDTOKEN") )
        {
        	return doPrimaryTransactionObject(trans);
        }
        return doPrimaryTransaction(trans);
    }
    public TransactionResponse captureTransaction(TransactionRequest trans)throws Exception{
        trans.setTransactionType(TransactionType.CAPTURE.name());
        if( (trans.getToken() != null) && (trans.getToken().getTokenType() != null) && (trans.getToken().getTokenType().toUpperCase() == "FDTOKEN") )
        {
        	return doSecondaryTransactionObject(trans);
        }
        return doSecondaryTransaction(trans);
    }
    public TransactionResponse refundTransaction(TransactionRequest trans)throws Exception{
        trans.setTransactionType(TransactionType.REFUND.name());
        if( (trans.getToken() != null) && (trans.getToken().getTokenType() != null) && (trans.getToken().getTokenType().toUpperCase() == "FDTOKEN") )
        {
        	return doSecondaryTransactionObject(trans);
        }
        return doSecondaryTransaction(trans);
    }
    public TransactionResponse voidTransaction(TransactionRequest trans)throws Exception{
        trans.setTransactionType(TransactionType.VOID.name());      
        if( (trans.getToken() != null) && (trans.getToken().getTokenType() != null) && (trans.getToken().getTokenType().toUpperCase() == "FDTOKEN") )
        {
        	return doSecondaryTransactionObject(trans);
        }
        return doSecondaryTransaction(trans);
    }
    
    public TransactionResponse getTokenTransaction(TransactionRequest trans) throws Exception{
        
        
        //if(trans.getToken().getTokenType() == "payeezy")
        //{
        	return doGetPrimaryTransaction(trans);
        //}
        //else
        //{
        //	return doGetTAPrimaryTransaction(trans);
        //}
    }
    public TransactionResponse postTokenTransaction(TransactionRequest trans) throws Exception {
        
        
        //if(trans.getToken().getTokenType() == "payeezy")
    	//{
    	//{
       	//return doPrimaryTransactionObject(trans);
        //}
        //else
        //{
        //	return doGetTAPrimaryTransaction(trans);
        //}
       	if(trans.getTransactionType() != null)
       	{
       		if  (trans.getTransactionType() == TransactionType.CAPTURE.name())
       		{
       			return doSecondaryTransactionObject(trans);
       		}
       		if(trans.getTransactionType() == TransactionType.REFUND.name()) 
       		{
       			return doSecondaryTransactionObject(trans);
       		}
       		if(trans.getTransactionType() == TransactionType.VOID.name() ) 
	       	{
	       		return doSecondaryTransactionObject(trans);
	       	}
       	}
       	return doPrimaryTransactionObject(trans);
    }
    
    
    private TransactionResponse GetTransactionResponse(Object obj)
    {
     	   TransactionResponse response = new TransactionResponse();
     	   Token token = new Token();
     	   Transarmor tokenData = new Transarmor();
     	   token.setTokenData(tokenData);
     	   response.setToken(token);
	       int beginIndex = 0;
	       int endIndex = 0;
     	   String objstr = obj.toString();
     	   boolean tokenResponse = false;
     	   objstr = objstr.trim();
     	   if(objstr.startsWith("Payeezy.callback"))
     	   {
     		   objstr = objstr.substring(19, objstr.length()); //("Payeezy.callback", "");
     		  objstr = objstr.trim();
     		   tokenResponse = true;
     	   }
     	   
     	   String[] responseData = objstr.split(",");
     	   
     	   for(int i=0;i<responseData.length;i++)
     	   {
     		   String str = responseData[i];
     		   
     		   String[] dataVals = str.split("=");
     		   if(tokenResponse)
     		   {
     			   str =str.trim();
     			  dataVals = str.split(":");
     		   }
     		   if(dataVals.length >=2)
     		   {
	       			dataVals[1] = dataVals[1].replace("{", "");
	       			dataVals[1] = dataVals[1].replace("}", "");
	       			dataVals[1] = dataVals[1].replace(":", "");
	       			dataVals[1] = dataVals[1].replace("\"", "");
	       			dataVals[1] = dataVals[1].replace("[", "");
     		   }
     		   if(dataVals.length >=3)
     		   {
	       			dataVals[2] = dataVals[2].replace("{", "");
	       			dataVals[2] = dataVals[2].replace("}", "");
	       			dataVals[2] = dataVals[2].replace(":", "");
	       			dataVals[2] = dataVals[2].replace("\"", "");
	       			dataVals[2] = dataVals[2].replace("[", "");
     		   }
     		   
     		   if(dataVals[0].contains("results"))
    		   {
		       	   String correlationID = dataVals[2];
    			   response.setCorrelationID(correlationID);
    		   }
     		   
     		   
     		   
     		   
     		   //if(str.contains("correlation_id"))
     		   if(dataVals[0].contains("correlation_id"))
     		   {
		       	   String correlationID = dataVals[1];
     			   response.setCorrelationID(correlationID);
     		   }
     		   if(str.contains("status"))
     		   {
     			   if(tokenResponse)
     			   {
     				    String status = dataVals[1];
     				    try
     				    {
     				    	int stat = Integer.parseInt(status);
     				    }
     				    catch(Exception e)
     				    {
     				    	System.out.println(e.getMessage());
     				    }
     				    if(status.length()<6)  //if(stat>0)
     				    {
     				    	response.setStatus(status);
     				    }
     			   }
     			   else
     			   {
			       	    String status = dataVals[1];
		       			response.setStatus(status);
     			   }
     		   }
     		   if(str.contains("type"))
     		   {
		       	    String type = dataVals[1];
	       			response.getToken().setTokenType(type);
     		   }
     		   if(str.contains("token"))
     		   {
		       	    String cardtype = dataVals[1];
		       	    if(dataVals.length >2)
		       	    {
		       		   cardtype = dataVals[2];
		       	    }
	       			response.getToken().getTokenData().setType(cardtype);
     		   }
     		   if(str.contains("cardholder_name"))
     		   {
		       	    String name = dataVals[1];
	       			response.getToken().getTokenData().setName(name);
     		   }
     		   
     		   if(str.contains("exp_date"))
     		   {
		       	    String expDate = dataVals[1];
	       			response.getToken().getTokenData().setExpiryDt(expDate);
     		   }
     		   if(str.contains("value"))
     		   {
		       	    String value = dataVals[1];
	       			response.getToken().getTokenData().setValue(value);
     		   }
     		   
     		   if(str.contains("transaction_id"))
     		   {
		       	    String transactionId = dataVals[1];
	       			response.setTransactionId(transactionId);
     		   }
     		   if(str.contains("transaction_tag"))
     		   {
		       	    String transactionTag = dataVals[1];
	       			response.setTransactionTag(transactionTag);
     		   }
     		   if(str.contains("amount"))
     		   {
		       	    String amount = dataVals[1];
	       			response.setAmount(amount);
     		   }
     		   if(str.contains("transaction_status"))
     		    {
		       	    String transactionStatus = dataVals[1];
	       			response.setTransactionStatus(transactionStatus);
     		    }
     			if(str.contains("validation_status"))
     		    {
		       	    String validation_status = dataVals[1];
	       			response.setValidationStatus(validation_status);
     		    }
     			if(str.contains("transaction_type"))
     		    {
		       	    String transaction_type = dataVals[1];
	       			response.setTransactionType(transaction_type);
     		    }
     			if(str.contains("method"))
     		    {
		       	    String method = dataVals[1];
	       			response.setMethod(method);
     		    }
     		
     	   }
     	   return response;
 	   
    }
 

}
