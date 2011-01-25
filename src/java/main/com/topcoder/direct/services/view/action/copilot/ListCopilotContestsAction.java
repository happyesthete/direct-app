/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotPostingContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the list of <code>Copilot Posting
 * </code> contests accessible to current user.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Manage Copilot Postings assembly)
 */
public class ListCopilotContestsAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>CopilotPostingContestsListDTO</code> providing the data to be displayed by Manage Copilot Postings
     * view.</p>
     */
    private CopilotPostingContestsListDTO viewData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>Constructs new <code>ListCopilotContestsAction</code> instance. This implementation does nothing.</p>
     */
    public ListCopilotContestsAction() {
        this.viewData = new CopilotPostingContestsListDTO();
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Gets the data to be displayed by Manage Copilot Postings view.</p>
     *
     * @return a <code>CopilotPostingContestsListDTO</code> providing the data to be displayed by Manage Copilot
     *         Postings view.
     */
    public CopilotPostingContestsListDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        // Get list of Copilot Posting contests available to current user
        getViewData().setContests(DataProvider.getCopilotPostingContests(currentUser));

        // For normal request flow prepare various data to be displayed to user
        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        getViewData().setUserProjects(userProjectsDTO);

        // Set current project context based on selected contest
        ProjectBriefDTO currentProject = this.sessionData.getCurrentProjectContext();
        if (currentProject != null) {
            List<TypedContestBriefDTO> contests
                = DataProvider.getProjectTypedContests(currentUser.getUserId(), currentProject.getId());
            this.sessionData.setCurrentProjectContests(contests);
        }
    }
}