/*
 * Copyright 2020 Rudy De Busscher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.payara.ee9.transform;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public final class MavenUtil {

    private MavenUtil() {
    }

    public static String defineLocalMavenRepository() {

        String mavenHome = System.getenv("M2_HOME");
        String userHome = System.getenv("HOME");

        File file = new File(userHome + "/.m2/settings.xml");
        String localRepositoryDefinition = null;
        try {
            localRepositoryDefinition = getLocalRepositoryDefinition(file);

            if (localRepositoryDefinition == null) {
                file = new File(mavenHome + "/conf/settings.xml");
                localRepositoryDefinition = getLocalRepositoryDefinition(file);

            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new UnexpectedException("Error during reading of settings.xml", e);
        }
        return localRepositoryDefinition;

    }

    private static String getLocalRepositoryDefinition(File file) throws ParserConfigurationException, SAXException, IOException {
        String result = null;

        if (!file.exists() || !file.canRead()) {
            return result;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        NodeList nodeList = doc.getElementsByTagName("localRepository");

        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            result = nodeList.item(itr).getTextContent();
        }
        return result;
    }
}
