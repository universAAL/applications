/**
 * FriendsBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public class FriendsBindingStub extends org.apache.axis.client.Stub implements na.oasisUtils.socialCommunity.Friends.FriendsPort {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[11];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getfriends");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "AuthToken"), na.oasisUtils.socialCommunity.Friends.AuthToken.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "GetFriendsResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.GetFriendsResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getuserprofile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "GetUserProfileInput"), na.oasisUtils.socialCommunity.Friends.GetUserProfileInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "GetUserProfileResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getgroups");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "AuthToken"), na.oasisUtils.socialCommunity.Friends.AuthToken.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "GroupsResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.GroupsResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getmygroups");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "AuthToken"), na.oasisUtils.socialCommunity.Friends.AuthToken.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "GroupsResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.GroupsResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addgroup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "AddGroupInput"), na.oasisUtils.socialCommunity.Friends.AddGroupInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "StatusResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("leavegroup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "GroupInput"), na.oasisUtils.socialCommunity.Friends.GroupInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "StatusResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("joingroup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "GroupInput"), na.oasisUtils.socialCommunity.Friends.GroupInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "StatusResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getgrouptopics");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "GroupInput"), na.oasisUtils.socialCommunity.Friends.GroupInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "GetGroupTopicsResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gettopic");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "GetTopicInput"), na.oasisUtils.socialCommunity.Friends.GetTopicInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "GetTopicResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.GetTopicResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addtopic");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "AddTopicInput"), na.oasisUtils.socialCommunity.Friends.AddTopicInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "StatusResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("posttotopic");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:Friends", "PostToTopicInput"), na.oasisUtils.socialCommunity.Friends.PostToTopicInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:Friends", "StatusResponse"));
        oper.setReturnClass(na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

    }

    public FriendsBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public FriendsBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public FriendsBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn:Friends", "AddGroupInput");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.AddGroupInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "AddTopicInput");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.AddTopicInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "AuthToken");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.AuthToken.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "Discussion");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.Discussion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "Discussions");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.Discussion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:Friends", "Discussion");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:Friends", "FriendsId");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:Friends", "GetFriendsResponse");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GetFriendsResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "GetGroupTopicsResponse");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "GetTopicInput");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GetTopicInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "GetTopicResponse");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GetTopicResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "GetUserProfileInput");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GetUserProfileInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "GetUserProfileResponse");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "Group");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.Group.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "GroupInput");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GroupInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "Groups");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.Group[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:Friends", "Group");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:Friends", "GroupsResponse");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.GroupsResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "NewGroup");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.NewGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "NewTopic");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.NewTopic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "PostToTopicInput");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.PostToTopicInput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "StatusResponse");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.StatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "Topic");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.Topic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "Topics");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.Topic[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:Friends", "Topic");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:Friends", "UserProfile");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.UserProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:Friends", "UserProfiles");
            cachedSerQNames.add(qName);
            cls = na.oasisUtils.socialCommunity.Friends.UserProfile[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:Friends", "UserProfile");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public na.oasisUtils.socialCommunity.Friends.GetFriendsResponse getfriends(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#getfriends");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "getfriends"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.GetFriendsResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.GetFriendsResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.GetFriendsResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse getuserprofile(na.oasisUtils.socialCommunity.Friends.GetUserProfileInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#getuserprofile");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "getuserprofile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.GroupsResponse getgroups(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#getgroups");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "getgroups"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.GroupsResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.GroupsResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.GroupsResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.GroupsResponse getmygroups(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#getmygroups");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "getmygroups"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.GroupsResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.GroupsResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.GroupsResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.StatusResponse addgroup(na.oasisUtils.socialCommunity.Friends.AddGroupInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#addgroup");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "addgroup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.StatusResponse leavegroup(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#leavegroup");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "leavegroup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.StatusResponse joingroup(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#joingroup");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "joingroup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse getgrouptopics(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#getgrouptopics");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "getgrouptopics"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.GetTopicResponse gettopic(na.oasisUtils.socialCommunity.Friends.GetTopicInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#gettopic");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "gettopic"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.GetTopicResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.GetTopicResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.GetTopicResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.StatusResponse addtopic(na.oasisUtils.socialCommunity.Friends.AddTopicInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#addtopic");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "addtopic"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public na.oasisUtils.socialCommunity.Friends.StatusResponse posttotopic(na.oasisUtils.socialCommunity.Friends.PostToTopicInput parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.xmlsoap.org/soap/envelope/#Friends#posttotopic");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/envelope/", "posttotopic"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (na.oasisUtils.socialCommunity.Friends.StatusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, na.oasisUtils.socialCommunity.Friends.StatusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
