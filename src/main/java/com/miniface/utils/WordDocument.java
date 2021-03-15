package com.miniface.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.services.FacePostServiceImpl;

public class WordDocument {

	public static ServletOutputStream CreateDocument(ServletOutputStream outStream) {

		try {
			XWPFDocument document = new XWPFDocument();

			XWPFParagraph title = document.createParagraph();
			title.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun titleRun = title.createRun();
			titleRun.setText("Document naslov");
			titleRun.setColor("009933");
			titleRun.setBold(true);
			titleRun.setFontFamily("Courier");
			titleRun.setFontSize(20);

			XWPFParagraph subTitle = document.createParagraph();
			subTitle.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun subTitleRun = subTitle.createRun();
			subTitleRun.setText("MiniFaceApp");
			subTitleRun.setColor("00CC44");
			subTitleRun.setFontFamily("Courier");
			subTitleRun.setFontSize(16);
			subTitleRun.setTextPosition(20);
			subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

			XWPFParagraph para1 = document.createParagraph();
			para1.setAlignment(ParagraphAlignment.BOTH);
			XWPFRun para1Run = para1.createRun();
			para1Run.setText("A ovo je paragraf");
			
			
			document.write(outStream);
			document.close();
			return outStream;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static void CreateDocumentDocx4j(ByteArrayOutputStream baos, int postID) {
		try {
			//String path = ContextListener.wordPostTemplatePath;
			WordprocessingMLPackage wordPackage = WordprocessingMLPackage.load(new File("C:\\Users\\darjan.crncic\\workspaces\\web-development\\MiniFaceApp\\postTemplate.docx"));
		
			MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

			FacePostServiceImpl fpsi = new FacePostServiceImpl();
			JSONObject post = fpsi.getPostByID(postID).getJSONObject(0);
			JSONArray comments = fpsi.getCommentsForPost(postID);
			JSONArray postLikes = fpsi.getLikes(postID, "postLikes");
			JSONArray commentLikes = fpsi.getLikes(postID, "commentLikes");
			
			Map<DataFieldName, String> variables = new HashMap<DataFieldName, String>();
			variables.put(new DataFieldName("title"), post.getString("TITLE"));
			variables.put(new DataFieldName("body"), post.getString("BODY"));
			variables.put(new DataFieldName("name"), post.getString("NAME"));
			variables.put(new DataFieldName("id"), Integer.toString(post.getInt("ID")));
			variables.put(new DataFieldName("creation_time"), post.getString("CREATION_TIME"));
			variables.put(new DataFieldName("likes"), Integer.toString(postLikes.getJSONObject(0).getInt("LIKES")));
			variables.put(new DataFieldName("comments"), Integer.toString(commentLikes.getJSONObject(0).getInt("LIKES")));

			MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);
			MailMerger.performMerge(wordPackage, variables, true);
			
			for(int i=0; i<comments.length(); i++) {
				mainDocumentPart.addStyledParagraphOfText("CommentTitle" ,(String) comments.getJSONObject(i).get("NAME"));
				mainDocumentPart.addStyledParagraphOfText("CommentBody", (String) comments.getJSONObject(i).get("POST_COMMENT"));
				mainDocumentPart.addStyledParagraphOfText("CommentTime", (String) comments.getJSONObject(i).get("CREATION_TIME"));
			}
			
			wordPackage.save(baos);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


//VariablePrepare.prepare(wordPackage);
//
//HashMap<String, String> variables = new HashMap<>();
//variables.put("title", post.getString("TITLE"));
//variables.put("body", post.getString("BODY"));
//variables.put("creator", post.getString("NAME"));
//variables.put("id", Integer.toString(post.getInt("ID")));
//variables.put("creation_time", post.getString("CREATION_TIME"));
//variables.put("likes", Integer.toString(postLikes.getJSONObject(0).getInt("LIKES")));
//variables.put("comments", Integer.toString(commentLikes.getJSONObject(0).getInt("LIKES")));
//
//mainDocumentPart.variableReplace(variables);
