package com.darin.servlet;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

public class RuntimeServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setIntHeader("Refresh", 5);

    response.setCharacterEncoding("utf-8");
    try
    {
      Sigar sigar = new Sigar();
      Mem mem = sigar.getMem();
      CpuPerc cpu = sigar.getCpuPerc();
      float total = (float)mem.getTotal();
      float used = (float)mem.getUsed();

      System.out.println("内存总量:   " + mem.getTotal() / 1024L / 1024L + 
        "M av");
      request.setAttribute("totalMeroy", mem.getTotal() / 1024L / 1024L + 
        "M");

      System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L / 1024L + 
        "M used");
      request.setAttribute("used", mem.getUsed() / 1024L / 1024L + 
        "M used");

      System.out.println("当前内存剩余量:    " + mem.getFree() / 1024L / 1024L + 
        "M free");
      request.setAttribute("free", mem.getFree() / 1024L / 1024L + 
        "M free");

      System.out.println("内存使用率:    " + used / total * 100.0F + "%");
      request.setAttribute("trend", used / total * 100.0F + "%");

      System.out.println("CPU总的使用率:   " + 
        CpuPerc.format(cpu.getCombined()));
      request.setAttribute("cpuTrend", CpuPerc.format(cpu.getCombined()));

      request.getRequestDispatcher("/show.jsp")
        .forward(request, response);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}