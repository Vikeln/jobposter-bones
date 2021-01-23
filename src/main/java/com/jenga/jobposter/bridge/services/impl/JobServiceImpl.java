package com.jenga.jobposter.bridge.services.impl;

import com.jenga.jobposter.bridge.dao.JobDao;
import com.jenga.jobposter.bridge.dao.JobResponseDao;
import com.jenga.jobposter.bridge.dao.UserDao;
import com.jenga.jobposter.bridge.entities.Job;
import com.jenga.jobposter.bridge.entities.JobResponse;
import com.jenga.jobposter.bridge.entities.User;
import com.jenga.jobposter.bridge.models.JobModel;
import com.jenga.jobposter.bridge.models.JobResponseModel;
import com.jenga.jobposter.bridge.models.Response;
import com.jenga.jobposter.bridge.models.SingleItemResponse;
import com.jenga.jobposter.bridge.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    @Value("${user-api-password}")
    private String apiPassword;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private JobResponseDao jobResponseDao;

    @Override
    public SingleItemResponse getUserJobs(Integer id) {

        Optional<User> optionalUser = userDao.findById(id);
        if (!optionalUser.isPresent())
            return SingleItemResponse.returnData(UserServiceImpl.notFoundStatus("User"), null);
        User user = optionalUser.get();
        return new SingleItemResponse(Response.SUCCESS.status(), jobDao.findAllByUserAndActive(user, true));
    }

    @Override
    public SingleItemResponse geJobs(Integer id) {
        return new SingleItemResponse(Response.SUCCESS.status(), jobDao.findById(id));
    }


    @Override
    public SingleItemResponse createJobResponse(JobResponseModel responseModel) {
        JobResponse response = responseModel.jobResponse();
        Optional<Job> optionalJob = jobDao.findById(responseModel.getJob());
        if (!optionalJob.isPresent())
            return SingleItemResponse.returnData(UserServiceImpl.notFoundStatus("Job"), null);
        response.setJob(optionalJob.get());

        response.setApplicantDocument(convertBase64ByteArray(responseModel.getUserDocument()));
        response.setApplicantDocumentName(getFileName(responseModel.getUserDocument()));

        return SingleItemResponse.returnData(Response.SUCCESS.status(), jobResponseDao.save(response));
    }

    @Override
    public Optional<JobResponse> getJobResponse(Integer jobResponse) {
        return jobResponseDao.findById(jobResponse);
    }

    @Override
    public SingleItemResponse markJobResponse(Integer jobResponse, String apiPassword) {
        if (!apiPassword.equalsIgnoreCase(apiPassword))
            return SingleItemResponse.returnData(Response.BAD_CREDS.status(), null);
        Optional<JobResponse> optionalJob = jobResponseDao.findById(jobResponse);
        if (!optionalJob.isPresent())
            return SingleItemResponse.returnData(UserServiceImpl.notFoundStatus("Job Application"), null);
        JobResponse jobResponsea = optionalJob.get();
        jobResponsea.setFollowedUp(true);
        return SingleItemResponse.returnData(Response.SUCCESS.status(), jobResponseDao.save(jobResponsea));
    }

    @Override
    public SingleItemResponse createJob(JobModel jobModel, String password) {
        if (!apiPassword.equalsIgnoreCase(password))
            return SingleItemResponse.returnData(Response.BAD_CREDS.status(), null);
        Job job = jobModel.getJob();
        Optional<User> optionalUser = userDao.findById(jobModel.getUser());
        if (!optionalUser.isPresent())
            return SingleItemResponse.returnData(UserServiceImpl.notFoundStatus("User"), null);
        if (jobDao.existsDistinctByJobTitle(jobModel.getTitle()))
            return SingleItemResponse.returnData(Response.TITLE_EXISTS.status(), null);
        User user = optionalUser.get();
        job.setUser(user);
        job.setJobBanner(convertBase64ByteArray(jobModel.getJobBanner()));
        job.setJobBannerName(getImageName(jobModel.getJobBanner()));
        return SingleItemResponse.returnData(Response.SUCCESS.status(), jobDao.save(job));
    }

    @Override
    public Job getJobByBBannerName(String name) {
        return jobDao.findDistinctByJobBannerNameContains(name);

    }

    public static byte[] convertBase64ByteArray(String rawString) {
        String[] base64String = convertStringToArray(rawString);
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(base64String[1]);
        return data;
    }

    public static String getFileName(String base64String) {
        String[] strings = convertStringToArray(base64String);
        String extension;
        String fileName = "";
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            case "data:image/gif;base64":
                extension = "gif";
                break;
            case "data:image/jpg;base64":
                extension = "jpg";
                break;
            default://should write cases for more images types
                extension = "pdf";
                break;
        }
        fileName = getTimestamp() + "." + extension;
        return fileName;
    }


    public static String getImageName(String base64String) {
        String[] strings = convertStringToArray(base64String);
        String extension;
        String fileName = "";
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            case "data:image/gif;base64":
                extension = "gif";
                break;
            case "data:image/jpg;base64":
                extension = "jpg";
                break;
            default://should write cases for more images types
                extension = "jpeg";
                break;
        }
        fileName = getTimestamp() + "." + extension;
        return fileName;
    }

    public static String getTimestamp() {
        final String TIMESTAMP_PATTERN = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_PATTERN);
        String date = formatter.format(new Date());
        return date;
    }

    public static String[] convertStringToArray(String base64String) {
        String[] stringArr = base64String.split(",");
        return stringArr;
    }
}
