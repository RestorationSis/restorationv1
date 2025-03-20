package com.restorationservice.restorationv1.model.customer.attachment;

public enum FileExtension {
  PDF("pdf"),
  DOC("doc"),
  DOCX("docx"),
  XLS("xls"),
  XLSX("xlsx"),
  PPT("ppt"),
  PPTX("pptx"),
  TXT("txt"),
  CSV("csv"),
  JPG("jpg"),
  JPEG("jpeg"),
  PNG("png"),
  GIF("gif"),
  BMP("bmp"),
  TIFF("tiff"),
  SVG("svg"),
  ZIP("zip"),
  RAR("rar"),
  TAR("tar"),
  GZ("gz"),
  JSON("json"),
  XML("xml"),
  HTML("html"),
  MP3("mp3"),
  WAV("wav"),
  MP4("mp4"),
  MKV("mkv"),
  AVI("avi");

  private final String extension;

  FileExtension(String extension) {
    this.extension = extension;
  }

  public String getExtension() {
    return extension;
  }
}
