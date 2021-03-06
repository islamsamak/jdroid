package com.jdroid.java.api;

import java.util.List;
import com.jdroid.java.http.HttpWebServiceProcessor;
import com.jdroid.java.http.MultipartWebService;
import com.jdroid.java.http.Server;
import com.jdroid.java.http.WebService;
import com.jdroid.java.http.apache.DefaultHttpClientFactory;
import com.jdroid.java.http.apache.HttpClientFactory;
import com.jdroid.java.http.apache.delete.ApacheHttpDeleteWebService;
import com.jdroid.java.http.apache.get.ApacheHttpGetWebService;
import com.jdroid.java.http.apache.patch.ApacheHttpPatchWebService;
import com.jdroid.java.http.apache.post.ApacheFormHttpPostWebService;
import com.jdroid.java.http.apache.post.ApacheHttpPostWebService;
import com.jdroid.java.http.apache.post.ApacheMultipartHttpPostWebService;
import com.jdroid.java.http.apache.put.ApacheHttpPutWebService;
import com.jdroid.java.http.apache.put.ApacheMultipartHttpPutWebService;
import com.jdroid.java.http.post.EntityEnclosingWebService;

public abstract class AbstractApacheApiService extends AbstractApiService {
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newGetService(java.lang.String,
	 *      com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected WebService newGetServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheHttpGetWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newPostService(java.lang.String,
	 *      com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected EntityEnclosingWebService newPostServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheHttpPostWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newPutServiceImpl(com.jdroid.java.http.Server, java.util.List,
	 *      com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected EntityEnclosingWebService newPutServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheHttpPutWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newMultipartPutServiceImpl(com.jdroid.java.http.Server,
	 *      java.util.List, com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected MultipartWebService newMultipartPutServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheMultipartHttpPutWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newMultipartPostServiceImpl(com.jdroid.java.http.Server,
	 *      java.util.List, com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected MultipartWebService newMultipartPostServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheMultipartHttpPostWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newDeleteServiceImpl(com.jdroid.java.http.Server, java.util.List,
	 *      com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected WebService newDeleteServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheHttpDeleteWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newFormPostServiceImpl(com.jdroid.java.http.Server, java.util.List,
	 *      com.jdroid.java.http.HttpWebServiceProcessor[])
	 */
	@Override
	protected EntityEnclosingWebService newFormPostServiceImpl(Server server, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheFormHttpPostWebService(getHttpClientFactoryInstance(), server, urlSegments,
				httpWebServiceProcessors);
	}
	
	/**
	 * @see com.jdroid.java.api.AbstractApiService#newPatchServiceImpl(com.jdroid.java.http.Server, java.util.List,
	 *      java.util.List)
	 */
	@Override
	protected EntityEnclosingWebService newPatchServiceImpl(Server baseURL, List<Object> urlSegments,
			List<HttpWebServiceProcessor> httpWebServiceProcessors) {
		return new ApacheHttpPatchWebService(getHttpClientFactoryInstance(), baseURL, urlSegments,
				httpWebServiceProcessors);
	}
	
	protected HttpClientFactory getHttpClientFactoryInstance() {
		return DefaultHttpClientFactory.get();
	}
}
