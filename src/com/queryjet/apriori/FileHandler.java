package com.queryjet.apriori;

/**
 * Created by Hyundai-HCC on 2016-04-01.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class FileHandler {

    private File file = null;
    private String dirName = null;
    private String fileName = null;
    private Properties properties = null;
    public void fileWriter(String dirName, String fileName) {
        this.dirName = dirName;
        this.fileName = fileName;
        try {
            this.file = new File(dirName);
            this.file.mkdirs();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public int fileRename(String dirName, String curFileName, String chgFileName) {
        File orgfile = new File(dirName, curFileName);
        File chgfile = new File(dirName, chgFileName);
        int result = 0;
        if (orgfile.renameTo(chgfile)) {
            result = 1;
        }
        return result;
    }

    public int fileRename(String curDirName, String chgDirName, String curFileName, String chgFileName) {
        File orgfile = new File(curDirName, curFileName);
        File chgfile = new File(chgDirName, chgFileName);
        int result = 0;
        if (orgfile.renameTo(chgfile)) {
            result = 1;
        }
        return result;
    }
    public boolean directoryCheck(String filePath, String fileName) {
        try {
            file = new File(filePath, fileName);
            if (!file.exists()) return false;
        } catch (Exception e) {
        }
        return true;
    }
    public final static char LF  = (char) 0x0A;
    public boolean insertContentFile(String contents, String filePath, String fileName) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

            fileWriter = new FileWriter(new File(filePath, fileName), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            //bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath,fileName)),"UTF8"));
            bufferedWriter.write(contents);
            bufferedWriter.write(LF);
            //bufferedWriter.newLine();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (Exception e) {
            }
        }
        return true;
    }
    public boolean insertContentFile2(String contents, String filePath, String fileName) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

         //   fileWriter = new FileWriter(new File(filePath, fileName), true);
            //bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath,fileName),true),"UTF8"));
            bufferedWriter.write(contents);
          //  bufferedWriter.write(LF);
            //bufferedWriter.newLine();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (Exception e) {
            }
        }
        return true;
    }
    public ArrayList<String> getfileData(String dirName, String fileName,Boolean isReadLine) {
        BufferedReader bufferedReader = null;
        String str = null;
        String cStr = null;
        List<String> strReturnData = new ArrayList<String>();

        try {
//            reCharSetByUtf8(new File(dirName, fileName));
            bufferedReader = new BufferedReader(new FileReader(new File(
                    dirName, fileName)));

            while (isReadLine == true) {
                str = bufferedReader.readLine();
                strReturnData.add(str);
                if (str == null)
                    break;
                // System.out.println(str);
            }

            if (isReadLine == false) {
                while (true) {
                    str = bufferedReader.readLine();
                    if (str == null)
                        break;
                    cStr = str;
                }
                strReturnData.add(cStr);
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
        return (ArrayList<String>) strReturnData;
    }


    public void reCharSetByUtf8(File file) throws IOException {

        String content = getFileContent(file);
        System.out.println(content);

        content = content.replaceAll("EUC-KR", "UTF-8");
        content = content.replaceAll("EUC_KR", "UTF-8");
        content = content.replaceAll("euc_kr", "UTF-8");
        content = content.replaceAll("euc-kr", "UTF-8");

        writeFileWithUTF8(file, content);
    }

    private String getFileContent(File file) throws IOException {

        int fileLength = (int) file.length();
        char[] c = new char[fileLength];

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String content = null;

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            br.read(c);
            content = new String(c);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (Exception e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
        }

        return content;
    }

    private void writeFileWithUTF8(File file, String content) {
        OutputStream outStream = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;

        try {
            outStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outStream, "UTF-8");
            bw = new BufferedWriter(writer);

            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (Exception e) {
                }
            }
        }
    }



    public void setDBXMLConfigFile(String url, String userName, String password) {

        Properties properties = new Properties();
        properties.setProperty("URL", url);
        properties.setProperty("USERNAME", userName);
        properties.setProperty("PASSWORD", password);
        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName,
                    fileName));

            properties.storeToXML(outputStream, "DB CONFIG PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
        }
    }


    /**
     *
     * @param propertyData use StringArrayType structure and 0 is key, 1 is value(It seems like hashmap type)
     * @param fileName //filename
     */
    public void setConfigFile(String[][] propertyData, String fileName){
        Properties properties = new Properties();
        int pData=propertyData.length;
        for (int i = 0; i < pData; i++) {
            properties.setProperty(propertyData[0][i], propertyData[1][i]);
        }
        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName,fileName+".xml"));
            properties.storeToXML(outputStream, "CONFIG PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
        }
    }

    /**
     *
     * @param propertyData use StringArrayType structure and 0 is key, 1 is value(It seems like hashmap type)
     * @param fileName //filename
     */
    public void setConfigFile(String[][] propertyData, String dirName, String fileName){
        Properties properties = new Properties();
        int pData=propertyData.length;
        for (int i = 0; i < pData; i++) {
            properties.setProperty(propertyData[i][0], propertyData[i][1]);
        }
        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName,fileName+".xml"));
            properties.storeToXML(outputStream, "CONFIG PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setInputTableXMLConfigFile(String tableName, String field1, String field2) {

        Properties properties = new Properties();
        properties.setProperty("TABLENAME", tableName);
        properties.setProperty("FIELD1", field1);
        properties.setProperty("FIELD2", field2);
        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName, "InputTableConfig.xml"));

            properties.storeToXML(outputStream, "DB CONFIG PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
        }
    }

    public void setOutputTableXMLConfigFile(String tableName, String field1, String field2, String field3) {

        Properties properties = new Properties();
        properties.setProperty("TABLENAME", tableName);
        properties.setProperty("FIELD1", field1);
        properties.setProperty("FIELD2", field2);
        properties.setProperty("FIELD3", field3);

        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName, "OutputTableConfig.xml"));

            properties.storeToXML(outputStream, "TABLE CONFIG PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
        }
    }

    public void setDataTypeXMLConfigFile(String dataType) {

        Properties properties = new Properties();
        if(dataType.toUpperCase() == "TXT"){
            properties.setProperty("DATATYPE", dataType);
            try {
                OutputStream outputStream = new FileOutputStream(new File(dirName, "DataTypeConfig.xml"));

                properties.storeToXML(outputStream, "DataType CONFIG PROPERTIES");
                outputStream.close();
            } catch (IOException e) {
            }
        }else{
            properties.setProperty("DATATYPE", dataType);
            try {
                OutputStream outputStream = new FileOutputStream(new File(dirName, "DataTypeConfig.xml"));

                properties.storeToXML(outputStream, "DataType CONFIG PROPERTIES");
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }


    public void getXMLReader(String dirName, String fileName){
        properties = new Properties();
        try{
            properties.loadFromXML(new FileInputStream(new File(dirName,fileName)));
        }catch(FileNotFoundException e){
        }catch(IOException e){
        }

    }


    public String getXMLProperty(String dataType){
        IOException ex = new IOException();
        if(properties == null){
            Throwable e = ex;
            System.out.println("Read to not found File");
            return null;
        }
        return (String)properties.get(dataType);
    }

    public void setSQLFile(String SQL1, String SQL2, String SQL3) {

        Properties properties = new Properties();
        properties.setProperty("SQL1", SQL1);
        properties.setProperty("SQL2", SQL2);
        properties.setProperty("SQL3", SQL3);

        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName, "SQLFILE.xml"));

            properties.storeToXML(outputStream, "DEFIED SQL PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
        }

    }

    public void setLogConfigFile(String logPath, String logFileName) {

        Properties properties = new Properties();
        properties.setProperty("LOGPATH", logPath);
        properties.setProperty("LOGFILENAME", logFileName);
        try {
            OutputStream outputStream = new FileOutputStream(new File(dirName, "LOGCONFIG.xml"));

            properties.storeToXML(outputStream, "LOGPATH PROPERTIES");
            outputStream.close();
        } catch (IOException e) {
        }
    }

    public String[] getDirList(String filePath){
        File dir = new File(filePath);
        File[] fileList = dir.listFiles();
        String[] fileListName = new String[fileList.length];
        int fileIdx = 0;
        try{
            for(int i = 0 ; i < fileList.length ; i++){
                File file = fileList[i];
                if(file.isFile()){
                    // ������ �ִٸ� ���� �̸� ���
                    fileListName[fileIdx]=file.getName();
                    fileIdx++;
                }else if(file.isDirectory()){
                    // ������丮�� �����ϸ� ����� ������� �ٽ� Ž��
                    getDirList(file.getCanonicalPath().toString());
                }
            }
        }catch(IOException e){

        }

        return fileListName;

    }

    public void fileDel(String filePath, String fileName){
        File file = new File(filePath, fileName);

        if(file.exists()){
            file.delete();
        }
    }

    public String mkdir(String mkFolder){
        //���丮 ����
        File desti = new File(mkFolder);

        if(!desti.exists()){
            desti.mkdirs();
        }else{
        }
        return mkFolder;
    }
    public static void fileMove(String orgPath, String orgFile, String chgPath, String chgFile){
        Path copyFrom = Paths.get(orgPath, orgFile);
        Path copyTo = Paths.get(chgPath, chgFile);
        try {
            Files.copy(copyFrom, copyTo, REPLACE_EXISTING);
            Files.delete(copyFrom);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}