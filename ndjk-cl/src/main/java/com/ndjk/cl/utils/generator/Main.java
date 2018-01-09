package com.ndjk.cl.utils.generator;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try {
			
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File("D:\\NDJK\\ndjk\\ndjk-cl\\src\\main\\java\\com\\ndjk\\cl\\utils\\generator\\mybaits-generator-mysql.xml");
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			
			//实例化一个MyBatisGenerator
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
					callback, warnings);
			
			
			myBatisGenerator.generate(null);
			System.out.println("--------------success------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
