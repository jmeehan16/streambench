name := "Stream Bench Word Count"

version := "1.0"

scalaVersion := "2.9.3"

libraryDependencies += "org.apache.spark" %% "spark-core" % "0.8.0-incubating"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "0.8.0-incubating"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

ivyXML := <dependency org="org.eclipse.jetty.orbit" name="javax.servlet" rev="2.5.0.v201103041518"> <artifact name="javax.servlet" type="orbit" ext="jar"/> </dependency>
