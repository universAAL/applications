package org.universAAL.AALapplication.medication_manager.user.management.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medication.MedicationOntology;
import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.service.ProfilingService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserManagerImpl {

  private static ServiceCaller caller = null;

  private static final String ARG_OUT = MedicationOntology.NAMESPACE + "argOut";


  // TODO: to be updated according to what we need

  private static final String OUTPUT_GETPROFILABLE = MedicationOntology.NAMESPACE
      + "out1";
  private static final String OUTPUT_GETPROFILE = MedicationOntology.NAMESPACE
      + "out2";
  private static final String OUTPUT_GETUSERS = MedicationOntology.NAMESPACE
      + "out3";
  private static final String OUTPUT_GETSUBPROFILES = MedicationOntology.NAMESPACE
      + "out4";
  private static final String OUTPUT_GETSUBPROFILE = MedicationOntology.NAMESPACE
      + "out5";
  private static final String USER_URI_PREFIX = "urn:org.universAAL.aal_space:test_env#";

  private static final String OUTPUT = MedicationOntology.NAMESPACE
      + "outX";

  public UserManagerImpl(ModuleContext context) {
    caller = new DefaultServiceCaller(context);
  }

  /**
   * add user to CHE via Profiling server
   *
   * @param user
   * @return ?
   */
  public String addUser(User user) {
    System.out.println("Profile Agent: add user with URI: " + user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS}, user);
    ServiceResponse resp = caller.call(req);
    return resp.getCallStatus().name();
  }

  /**
   * get user from CHE
   *
   * @param user
   * @return User instance, if exists; null, if not exists (registered)
   */
  public User getUser(User user) {
    System.out.println("Profile agent: get user: " + user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addRequiredOutput(OUTPUT_GETPROFILABLE, new String[]{ProfilingService.PROP_CONTROLS});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETPROFILABLE);
      if (out != null) {
        return (User) out;
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("other results: " + resp.getCallStatus().name());
      return null;
    }
  }

  public User getUser(String uri) {
    System.out.println("Profile agent: get user with URI: " + uri);
    User user = new User(uri);
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addRequiredOutput(OUTPUT_GETPROFILABLE, new String[]{ProfilingService.PROP_CONTROLS});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETPROFILABLE);
      if (out != null) {

        System.out.println("Profile agent: result got is - " + out.toString());
        List users = (List) out;
        if (users.isEmpty()) {
          return null;
        }

        User us = null;
        for (int i = 0; i < users.size(); i++) {
          User user1 = (User) users.get(i);
          if (uri.equals(user1.getURI())) {
            us = user1;
          }
        }
        return us;
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("other results: " + resp.getCallStatus().name());
      return null;
    }
  }

  public List<User> getAllUsers() {
    System.out.println("Profile Agent: getAllUsers");
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addRequiredOutput(OUTPUT_GETUSERS, new String[]{ProfilingService.PROP_CONTROLS});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETUSERS);
      if (out != null) {
        System.out.println(out.toString());
        // get each user using the uri
        List<User> users = new ArrayList();
        List outl = (List) out;
        Object ur;
        //System.out.println("Profile Agent: the output size: " + outl.size());
        for (int i = 0; i < outl.size(); i++) {
          ur = (Object) outl.get(i);
          //System.out.println("Has a user " + ur);
          User u = getUser(ur.toString());
          //System.out.println("Profile Agent: get an user: " + u.getURI());
          users.add(u);
        }
        return users;
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("Other results: " + resp.getCallStatus().name());
      return null;
    }
  }

  public Set<User> getUsers() {
    ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
    req.addRequiredOutput(ARG_OUT, new String[]{ProfilingService.PROP_CONTROLS});
    ServiceResponse resp = caller.call(req);
    System.out.println("resp.getCallStatus() = " + resp.getCallStatus());
    return null;
  }

  public void getHealthProfile(String userURI) {

    ServiceRequest req = new ServiceRequest(new ProfilingService(null), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, new User(userURI));
    req.addRequiredOutput(ARG_OUT, new String[]{
        ProfilingService.PROP_CONTROLS,
        Profilable.PROP_HAS_PROFILE,
        Profile.PROP_HAS_SUB_PROFILE});

    ServiceResponse sr = caller.call(req);
    if (sr.getCallStatus() == CallStatus.succeeded) {
      try {
        List<?> subProfiles = sr.getOutput(ARG_OUT, true);
        if (subProfiles == null || subProfiles.size() == 0) {
          return;
        }
        Log.info("searching in " + subProfiles.size() + " suprofiles", getClass());
        Iterator<?> iter = subProfiles.iterator();
        while (iter.hasNext()) {
          SubProfile subProfile = (SubProfile) iter.next();
          System.out.println("subProfile = " + subProfile);
        }
      } catch (Exception e) {
        throw new MedicationManagerUserManagementException(e);
      }
    } else {
      Log.info("callstatus is not succeeded", this.getClass());
    }
  }

  public boolean updateUser(User user) {
    System.out.println("Profile Agent: update user: " + user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addChangeEffect(new String[]{ProfilingService.PROP_CONTROLS}, user);
    ServiceResponse resp = caller.call(req);
    System.out.println("The result: " + resp.getCallStatus().name());
    if (resp.getCallStatus() == CallStatus.succeeded)
      return true;
    else return false;
  }

  public boolean deleteUser(String uri) {
    System.out.println("Profile Agent: delete User: " + uri);
    User user = new User(uri);
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
        ProfilingService.PROP_CONTROLS, user);
    req.getRequestedService().addInstanceLevelRestriction(r1, new String[]{ProfilingService.PROP_CONTROLS});
    req.addRemoveEffect(new String[]{ProfilingService.PROP_CONTROLS});
    ServiceResponse resp = caller.call(req);
    System.out.println("The result: " + resp.getCallStatus().name());
    if (resp.getCallStatus() == CallStatus.succeeded)
      return true;
    else return false;
  }

  private ServiceRequest userProfileRequest(User user) {
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);

    req.addTypeFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, UserProfile.MY_URI);

    req.addRequiredOutput(OUTPUT_GETPROFILE, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE});

    return req;
  }


  private Object getReturnValue(List outputs, String expectedOutput) {
    Object returnValue = null;
    if (outputs == null)
      System.out.println("Profile Agent: No results found!");
    else
      for (Iterator i = outputs.iterator(); i.hasNext(); ) {
        ProcessOutput output = (ProcessOutput) i.next();
        if (output.getURI().equals(expectedOutput))
          if (returnValue == null)
            returnValue = output.getParameterValue();
          else
            System.out.println("Profile Agent: redundant return value!");
        else
          System.out.println("Profile Agent - output ignored: " + output.getURI());
      }

    return returnValue;
  }

  public String addUserProfile(UserProfile profile) {
    System.out.println("Profile Agent: add user Profile: " + profile.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, profile);
    ServiceResponse resp = caller.call(req);
    return resp.getCallStatus().name();
  }

  public String getUserProfile(User user) {
    System.out.println("Profile agent: get user profile for user: " + user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addRequiredOutput(OUTPUT_GETPROFILE, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETPROFILE);
      if (out != null) {
        System.out.println("The result: " + out.toString());
        return out.toString();
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("Other result: " + resp.getCallStatus().name());
      return null;
    }
  }


  public String addUserProfile(User user, UserProfile profile) {
    System.out.println("Profile agent: addProfile to an user");
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, profile);
    ServiceResponse resp = caller.call(req);
    return resp.getCallStatus().name();
  }

  public void changeUserProfile(User user, UserProfile userProfile) {
    // TODO Auto-generated method stub

  }

  public void removeUserProfile(User user) {
    // TODO Auto-generated method stub

  }

  public List getAALSpaceProfiles(User user) {
    // TODO Auto-generated method stub
    return null;
  }

  public void addAALSpaceProfile(User user, AALSpaceProfile aalSpaceProfile) {
    // TODO Auto-generated method stub

  }

  public void changeAALSpaceProfile(User user, AALSpaceProfile aalSpaceProfile) {
    // TODO Auto-generated method stub

  }

  public void removeAALSpaceProfile(User user) {
    // TODO Auto-generated method stub

  }

  public String addSubProfile(SubProfile profile) {
    System.out.println("Profile agent: add subProfile" + profile.getPropertyURIs().toString());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, profile);
    ServiceResponse resp = caller.call(req);
    return resp.getCallStatus().name();
  }

  public SubProfile getSubProfile(SubProfile profile) {
    System.out.println("Profile Agent: Get SubProfile");
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, profile);
    req.addRequiredOutput(OUTPUT_GETSUBPROFILE, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETSUBPROFILE);
      if (out != null) {
        System.out.println(out.toString());
        return (SubProfile) out;
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("The result is: " + resp.getCallStatus().name());
      return null;
    }
  }

  public String getUserSubprofiles(User user) {
    System.out.println("Profile agent: get all Subprofiles for user: " + user.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addRequiredOutput(OUTPUT_GETSUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETSUBPROFILES);
      if (out != null) {
        System.out.println(out.toString());
        return out.toString();
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("Other results: " + resp.getCallStatus().name());
      return null;
    }
  }

  private String getSubProfile(String urn) {
    System.out.println("Profile Agent: call GetSubProfile using urn");
    SubProfile profile = new SubProfile(urn);
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, profile);
    req.addRequiredOutput(OUTPUT_GETSUBPROFILE, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETSUBPROFILE);
      if (out != null) {
        System.out.println(out.toString());
        return out.toString();
      } else {
        System.out.println("NOTHING!");
        return "nothing";
      }
    } else {
      return resp.getCallStatus().name();
    }
  }

  public String addUserSubprofile(User user, SubProfile subProfile) {
    System.out.println("Profile agent: add subProfile for user: " + user.getURI() + " subProfile: " + subProfile.toString());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, subProfile);
    ServiceResponse resp = caller.call(req);
    return resp.getCallStatus().name();
  }

  public void changeUserSubprofile(User user, SubProfile subProfile) {
    // TODO Auto-generated method stub

  }

  public void removeUserSubprofile(User user, String subprofile_URI) {
    // TODO Auto-generated method stub

  }

  public List getUserSubprofiles(UserProfile profile) {
    System.out.println("Profile Agent: get Subprofiles for userprofile: " + profile.getURI());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, profile);
    req.addTypeFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, SubProfile.MY_URI);
    req.addRequiredOutput(OUTPUT_GETSUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE});
    ServiceResponse resp = caller.call(req);
    if (resp.getCallStatus() == CallStatus.succeeded) {
      //System.out.println(resp.getOutputs());
      Object out = getReturnValue(resp.getOutputs(), OUTPUT_GETSUBPROFILES);
      if (out != null) {
        System.out.println(out.toString());
        return (List) out;
      } else {
        System.out.println("NOTHING!");
        return null;
      }
    } else {
      System.out.println("Results: " + resp.getCallStatus().name());
      return null;
    }
  }

  public String addUserSubprofile(UserProfile userProfile, SubProfile subProfile) {
    System.out.println("Profile Agent: add subprofile for userProfile: " + userProfile.getURI() + " subprofile: " + subProfile.toString());
    ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
    req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, userProfile);
    req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE}, subProfile);
    ServiceResponse resp = caller.call(req);
    return resp.getCallStatus().name();
  }


  /**
   * get user profile for user with userId
   * (use Profiling server)
   *
   * @return String representation of the User profile
   *         TODO: check with ustore the format
   */
  public String getUserProfile(String userID) {
    String userURI = USER_URI_PREFIX + userID;
    User user = new User(userURI);

    ServiceResponse sr = caller.call(userProfileRequest(user));

    if (sr.getCallStatus() == CallStatus.succeeded) {
      try {
        List outputAsList = sr.getOutput(OUTPUT_GETPROFILE, true);

        if ((outputAsList == null) || (outputAsList.size() == 0)) {
          return null;
        }
        // TODO: convert the UserProfile result to some structure?
        return outputAsList.get(0).toString();
      } catch (Exception e) {
        return null;
      }
    }
    return null;
  }

  /**
   * * utility functions ****
   */


  private String getListOfResults(ServiceResponse resp) {
    if (resp.getCallStatus() == CallStatus.succeeded) {
      Object out = getReturnValue(resp.getOutputs(), OUTPUT);
      if (out != null) {
        System.out.println(out.toString());
        return out.toString();
      } else {
        System.out.println("NOTHING!");
        return "nothing";
      }
    } else {
      return resp.getCallStatus().name();
    }
  }

}
