create table bookmarks
(
    id         bigserial primary key,
    title      varchar not null,
    url        varchar not null,
    created_at timestamp,
    updated_at timestamp
);

INSERT INTO bookmarks(title, url, created_at) VALUES
('How (not) to ask for Technical Help?','https://sivalabs.in/how-to-not-to-ask-for-technical-help', CURRENT_TIMESTAMP),
('Announcing My SpringBoot Tips Video Series on YouTube','https://sivalabs.in/announcing-my-springboot-tips-video-series', CURRENT_TIMESTAMP),
('Kubernetes - Exposing Services to outside of Cluster using Ingress','https://sivalabs.in/kubernetes-ingress', CURRENT_TIMESTAMP),
('Kubernetes - Blue/Green Deployments','https://sivalabs.in/kubernetes-blue-green-deployments', CURRENT_TIMESTAMP),
('Kubernetes - Releasing a new version of the application using Deployment Rolling Updates','https://sivalabs.in/kubernetes-deployment-rolling-updates', CURRENT_TIMESTAMP),
('Getting Started with Kubernetes','https://sivalabs.in/getting-started-with-kubernetes', CURRENT_TIMESTAMP),
('Get Super Productive with Intellij File Templates','https://sivalabs.in/get-super-productive-with-intellij-file-templates', CURRENT_TIMESTAMP),
('Few Things I learned in the HardWay in 15 years of my career','https://sivalabs.in/few-things-i-learned-the-hardway-in-15-years-of-my-career', CURRENT_TIMESTAMP),
('All the resources you ever need as a Java & Spring application developer','https://sivalabs.in/all-the-resources-you-ever-need-as-a-java-spring-application-developer', CURRENT_TIMESTAMP),
('GoLang from a Java developer perspective','https://sivalabs.in/golang-from-a-java-developer-perspective', CURRENT_TIMESTAMP),
('Imposing Code Structure Guidelines using ArchUnit','https://sivalabs.in/impose-architecture-guidelines-using-archunit', CURRENT_TIMESTAMP),
('SpringBoot Integration Testing using TestContainers Starter','https://sivalabs.in/spring-boot-integration-testing-using-testcontainers-starter', CURRENT_TIMESTAMP),
('Creating Yeoman based SpringBoot Generator','https://sivalabs.in/creating-yeoman-based-springboot-generator', CURRENT_TIMESTAMP),
('Testing REST APIs using Postman and Newman','https://sivalabs.in/testing-rest-apis-with-postman-newman', CURRENT_TIMESTAMP),
('Testing SpringBoot Applications','https://sivalabs.in/spring-boot-testing', CURRENT_TIMESTAMP)
;