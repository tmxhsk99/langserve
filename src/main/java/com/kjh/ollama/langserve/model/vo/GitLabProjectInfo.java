package com.kjh.ollama.langserve.model.vo;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.ProjectLicense;
import org.gitlab4j.api.models.*;
import org.gitlab4j.api.utils.JacksonJson;
import org.gitlab4j.api.utils.JacksonJsonEnumHelper;

@Getter
@Setter
public class GitLabProjectInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 12L;

    private Integer approvalsBeforeMerge;
    private Boolean archived;
    private String avatarUrl;
    private Boolean containerRegistryEnabled;
    private Date createdAt;
    private Long creatorId;
    private String defaultBranch;
    private String description;

    private Integer forksCount;

    private GitLabProjectInfo forkedFromProject;
    private String httpUrlToRepo;
    private Long id;
    private Boolean isPublic;
    private Boolean issuesEnabled;
    private Boolean jobsEnabled;
    private Date lastActivityAt;
    private Boolean lfsEnabled;
    private GitLabProjectInfo.MergeMethod mergeMethod;
    private Boolean mergeRequestsEnabled;
    private String name;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = String.class, name = "string"),
            @JsonSubTypes.Type(value = Namespace.class, name = "object")
    })
    private Object namespace;
    private String nameWithNamespace;
    private Boolean onlyAllowMergeIfPipelineSucceeds;
    private Boolean allowMergeOnSkippedPipeline;
    private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;
    private Integer openIssuesCount;
    private Owner owner;
    private String path;
    private String pathWithNamespace;
    private Permissions permissions;
    private Boolean publicJobs;
    private String repositoryStorage;
    private Boolean requestAccessEnabled;
    private String runnersToken;
    private Boolean sharedRunnersEnabled;
    private List<SharedGroup> sharedWithGroups;
    private Boolean snippetsEnabled;
    private String sshUrlToRepo;
    private Integer starCount;
    private List<String> tagList;
    private List<String> topics;
    private Integer visibilityLevel;
    private Visibility visibility;
    private Boolean wallEnabled;
    private String webUrl;
    private Boolean wikiEnabled;
    private Boolean printingMergeRequestLinkEnabled;
    private Boolean resolveOutdatedDiffDiscussions;
    private ProjectStatistics statistics;
    private Boolean initializeWithReadme;
    private Boolean packagesEnabled;
    private Boolean emptyRepo;
    private String licenseUrl;
    private ProjectLicense license;
    private List<CustomAttribute> customAttributes;
    private String buildCoverageRegex;
    private Constants.BuildGitStrategy buildGitStrategy;
    private String readmeUrl;
    private Boolean canCreateMergeRequestIn;
    private ImportStatus.Status importStatus;
    private Integer ciDefaultGitDepth;
    private Boolean ciForwardDeploymentEnabled;
    private String ciConfigPath;
    private Boolean removeSourceBranchAfterMerge;
    private Boolean autoDevopsEnabled;
    private Constants.AutoDevopsDeployStrategy autoDevopsDeployStrategy;
    private Boolean autocloseReferencedIssues;
    private Boolean emailsDisabled;
    private String suggestionCommitMessage;
    private Constants.SquashOption squashOption;
    private String mergeCommitTemplate;
    private String squashCommitTemplate;
    private String issueBranchTemplate;
    @JsonProperty("_links")
    private Map<String, String> links;
    @JsonSerialize(
            using = JacksonJson.DateOnlySerializer.class
    )
    private Date markedForDeletionOn;

    public GitLabProjectInfo() {
    }

    public GitLabProjectInfo withApprovalsBeforeMerge(Integer approvalsBeforeMerge) {
        this.approvalsBeforeMerge = approvalsBeforeMerge;
        return this;
    }

    public GitLabProjectInfo withContainerRegistryEnabled(boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
        return this;
    }


    public GitLabProjectInfo withDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
        return this;
    }


    public GitLabProjectInfo withDescription(String description) {
        this.description = description;
        return this;
    }

    public GitLabProjectInfo withId(Long id) {
        this.id = id;
        return this;
    }

    public GitLabProjectInfo withIssuesEnabled(boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
        return this;
    }

    public GitLabProjectInfo withJobsEnabled(boolean jobsEnabled) {
        this.jobsEnabled = jobsEnabled;
        return this;
    }

    public GitLabProjectInfo withLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
        return this;
    }

    public GitLabProjectInfo withMergeMethod(GitLabProjectInfo.MergeMethod mergeMethod) {
        this.mergeMethod = mergeMethod;
        return this;
    }

    public GitLabProjectInfo withMergeRequestsEnabled(boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
        return this;
    }

    public GitLabProjectInfo withName(String name) {
        this.name = name;
        return this;
    }

    public GitLabProjectInfo withNamespace(Namespace namespace) {
        this.namespace = namespace;
        return this;
    }

    public GitLabProjectInfo withNamespaceId(long namespaceId) {
        this.namespace = new Namespace();
        Namespace namespace1 = (Namespace) namespace;
        namespace1.setId(namespaceId);
        return this;
    }

    public GitLabProjectInfo withOnlyAllowMergeIfPipelineSucceeds(Boolean onlyAllowMergeIfPipelineSucceeds) {
        this.onlyAllowMergeIfPipelineSucceeds = onlyAllowMergeIfPipelineSucceeds;
        return this;
    }

    public GitLabProjectInfo withAllowMergeOnSkippedPipeline(Boolean allowMergeOnSkippedPipeline) {
        this.allowMergeOnSkippedPipeline = allowMergeOnSkippedPipeline;
        return this;
    }

    public GitLabProjectInfo withOnlyAllowMergeIfAllDiscussionsAreResolved(Boolean onlyAllowMergeIfAllDiscussionsAreResolved) {
        this.onlyAllowMergeIfAllDiscussionsAreResolved = onlyAllowMergeIfAllDiscussionsAreResolved;
        return this;
    }

    public GitLabProjectInfo withPath(String path) {
        this.path = path;
        return this;
    }


    public Boolean getPublic() {
        return this.isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public GitLabProjectInfo withPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }


    public GitLabProjectInfo withPublicJobs(boolean publicJobs) {
        this.publicJobs = publicJobs;
        return this;
    }

    public GitLabProjectInfo withRepositoryStorage(String repositoryStorage) {
        this.repositoryStorage = repositoryStorage;
        return this;
    }


    public GitLabProjectInfo withRequestAccessEnabled(boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
        return this;
    }

    public GitLabProjectInfo withSharedRunnersEnabled(boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
        return this;
    }


    public GitLabProjectInfo withSnippetsEnabled(boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
        return this;
    }


    public GitLabProjectInfo withTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }


    public GitLabProjectInfo withVisibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public GitLabProjectInfo withVisibilityLevel(Integer visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
        return this;
    }


    public GitLabProjectInfo withWallEnabled(Boolean wallEnabled) {
        this.wallEnabled = wallEnabled;
        return this;
    }

    public GitLabProjectInfo withWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }


    public GitLabProjectInfo withWikiEnabled(boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
        return this;
    }


    public GitLabProjectInfo withPrintingMergeRequestLinkEnabled(Boolean printingMergeRequestLinkEnabled) {
        this.printingMergeRequestLinkEnabled = printingMergeRequestLinkEnabled;
        return this;
    }


    public GitLabProjectInfo withResolveOutdatedDiffDiscussions(boolean resolveOutdatedDiffDiscussions) {
        this.resolveOutdatedDiffDiscussions = resolveOutdatedDiffDiscussions;
        return this;
    }

    public GitLabProjectInfo withInitializeWithReadme(boolean initializeWithReadme) {
        this.initializeWithReadme = initializeWithReadme;
        return this;
    }

    public GitLabProjectInfo withPackagesEnabled(Boolean packagesEnabled) {
        this.packagesEnabled = packagesEnabled;
        return this;
    }

    public static final boolean isValid(GitLabProjectInfo project) {
        return project != null && project.getId() != null;
    }

    public String toString() {
        return JacksonJson.toJsonString(this);
    }

    public static final String getPathWithNammespace(String namespace, String path) {
        String var10000 = namespace.trim();
        return var10000 + "/" + path.trim();
    }


    public GitLabProjectInfo withBuildCoverageRegex(String buildCoverageRegex) {
        this.buildCoverageRegex = buildCoverageRegex;
        return this;
    }

    public GitLabProjectInfo withBuildGitStrategy(Constants.BuildGitStrategy buildGitStrategy) {
        this.buildGitStrategy = buildGitStrategy;
        return this;
    }


    public GitLabProjectInfo withRemoveSourceBranchAfterMerge(Boolean removeSourceBranchAfterMerge) {
        this.removeSourceBranchAfterMerge = removeSourceBranchAfterMerge;
        return this;
    }


    public GitLabProjectInfo withEmailsDisabled(Boolean emailsDisabled) {
        this.emailsDisabled = emailsDisabled;
        return this;
    }

    public GitLabProjectInfo withSuggestionCommitMessage(String suggestionCommitMessage) {
        this.suggestionCommitMessage = suggestionCommitMessage;
        return this;
    }

    public GitLabProjectInfo withSquashOption(Constants.SquashOption squashOption) {
        this.squashOption = squashOption;
        return this;
    }


    @JsonIgnore
    public String getLinkByName(String name) {
        return this.links != null && !this.links.isEmpty() ? (String)this.links.get(name) : null;
    }

    public static enum MergeMethod {
        MERGE,
        REBASE_MERGE,
        FF;

        private static JacksonJsonEnumHelper<GitLabProjectInfo.MergeMethod> enumHelper = new JacksonJsonEnumHelper(GitLabProjectInfo.MergeMethod.class);

        private MergeMethod() {
        }

        @JsonCreator
        public static GitLabProjectInfo.MergeMethod forValue(String value) {
            return (GitLabProjectInfo.MergeMethod)enumHelper.forValue(value);
        }

        @JsonValue
        public String toValue() {
            return enumHelper.toString(this);
        }

        public String toString() {
            return enumHelper.toString(this);
        }
    }
}