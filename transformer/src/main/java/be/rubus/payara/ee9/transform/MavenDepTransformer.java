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

import org.eclipse.transformer.Transformer;
import org.eclipse.transformer.jakarta.JakartaTransformer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class MavenDepTransformer {


    private String mavenRepoLocation = MavenUtil.defineLocalMavenRepository();

    public static void main(String[] args) {
        /*
        Transformer jTrans = new Transformer(System.out, System.err);
        jTrans.setOptionDefaults(JakartaTransformer.class, JakartaTransformer.getOptionDefaults());
        args = new String[2];
        args[0] = "/Users/rubus/mvn_repo/org/eclipse/microprofile/rest/client/microprofile-rest-client-api/1.4.1/microprofile-rest-client-api-1.4.1.jar";
        args[1] = "/Users/rubus/mvn_repo/org/eclipse/microprofile/rest/client/microprofile-rest-client-api/1.4.1/microprofile-rest-client-api-1.4.1-jakarta.jar";
        jTrans.setArgs(args);
        int rc = jTrans.run();

         */
    }

    public void transform() {
        for (String dependency : readDependencies()) {
            transformDependency(dependency);
        }


    }

    private void transformDependency(String dependency) {
        String[] parts = dependency.split(":");

        String source = defineFile(parts, false);
        File sourceFile = new File(source);
        if (sourceFile.exists() && sourceFile.canRead()) {


            String target = defineFile(parts, true);

            Transformer jTrans = new Transformer(System.out, System.err);
            jTrans.setOptionDefaults(JakartaTransformer.class, JakartaTransformer.getOptionDefaults());
            String[] args = new String[2];
            args[0] = source;
            args[1] = target;
            jTrans.setArgs(args);
            int rc = jTrans.run();

        }

    }

    private String defineFile(String[] mavenIdentifiers, boolean classifier) {
        StringBuilder result = new StringBuilder();
        result.append(mavenRepoLocation);
        result.append("/");
        result.append(mavenIdentifiers[0].replaceAll("\\.", "/"));
        result.append("/");
        result.append(mavenIdentifiers[1]);
        result.append("/");
        result.append(mavenIdentifiers[2]);
        result.append("/");
        result.append(mavenIdentifiers[1]);
        result.append("-");
        result.append(mavenIdentifiers[2]);
        if (classifier) {
            result.append("-jakarta");
        }
        result.append(".jar");
        return result.toString();
    }

    private String suffix(String mavenIdentifier) {
        String[] parts = mavenIdentifier.split("\\.");
        return parts[parts.length - 1];
    }

    private List<String> readDependencies() {
        InputStream stream = MavenDepTransformer.class.getResourceAsStream("/transform.deps");
        List<String> result = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
        try {
            stream.close();
        } catch (IOException e) {
            throw new UnexpectedException("Error during reading 'transform.deps' file", e);
        }
        return result;
    }
}
