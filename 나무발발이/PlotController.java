package com.bit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.rcaller.rStuff.RCaller;
import com.github.rcaller.rStuff.RCode;

@Controller
public class PlotController {

	@RequestMapping("/Plot")
	@ResponseBody
	public String plot(String code, HttpServletRequest request)
	{
		String path = request.getRealPath("resources/img");
		String str = "ok";
		try{
			RCaller caller = new RCaller();			
			caller.setRscriptExecutable("C:/Program Files/R/R-3.5.2/bin/x64/Rscript.exe");			
			RCode rcode = new RCode();			
			rcode.clear();
			File file;			
			file = rcode.startPlot();	
			
			rcode.addRCode("library(DBI)");
			rcode.addRCode("library(RODBC)");
			rcode.addRCode("db = odbcConnect('bit_oracle','madang','madang')");			
			
			if(code.equals("pub"))
			{
				rcode.addRCode("df1 = sqlQuery(db, 'select publisher, sum(saleprice) sum from book, orders where book.bookid = orders.bookid group by publisher')");
				rcode.addRCode("plot(df1$SUM, ylim=c(10000,max(df1$SUM)), axes=FALSE,ann=FALSE)");
				rcode.addRCode("axis(1,at=1:nrow(df1),lab=df1$PUBLISHER)");
				rcode.addRCode("axis(2,ylim=c(10000,max(df1$SUM)))");
				rcode.addRCode("title(main='출판사별 총판매금액')");
				rcode.addRCode("title(xlab='출판사명')");
				rcode.addRCode("title(ylab='총판매금액')");
			}
			else 
			{
				rcode.addRCode("df1 = sqlQuery(db,'select bookname, sum(saleprice) sum from book, orders where book.bookid = orders.bookid group by bookname')");
				rcode.addRCode("plot(df1$SUM, ylim=c(min(df1$SUM),max(df1$SUM)), axes=FALSE,ann=FALSE)");
				rcode.addRCode("axis(1,at=1:nrow(df1),lab=df1$PUBLISHER)");
				rcode.addRCode("axis(2,ylim=c(min(df1$SUM),max(df1$SUM)))");
				rcode.addRCode("title(main='도서별 총판매금액')");
				rcode.addRCode("title(xlab='도서명')");
				rcode.addRCode("title(ylab='총판매금액')");
			}
			
			//rcode.addRCode("savePlot('"+path+"/"+code+".png',type='png')");
			
			rcode.endPlot();			
			caller.setRCode(rcode);
			caller.runOnly();
			
			System.out.println(file);
			//String fname = file.getName();
			
			
			FileInputStream fis = new FileInputStream(file);
			
			System.out.println("path:"+path);
			FileOutputStream fos = new FileOutputStream(path + "/"+code+".png");
			FileCopyUtils.copy(fis, fos);
			fis.close();
			fos.close();
		
		}catch(Exception e)
		{
			System.out.println(e.getMessage());		
		}		
		return str;
	}
}
