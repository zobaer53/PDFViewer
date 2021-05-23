package com.example.zobaer53.pdfviewer;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import java.io.File;

public class ViewActivity extends AppCompatActivity {
    PDFView pdfView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        pdfView = findViewById(R.id.pdfViewer);
        progressBar = findViewById(R.id.progressBar);


        if(getIntent() != null){

           String viewType = getIntent().getStringExtra("ViewType");
             if(viewType != null && !TextUtils.isEmpty(viewType)){
                  if(viewType.equals("assets")){
                      pdfView.fromAsset("my_resume.pdf")
                              .enableSwipe(true)
                              .swipeHorizontal(false)
                              .enableDoubletap(true)
                              .onPageError((page, t) -> Toast.makeText(ViewActivity.this,"Error while opening"+page,Toast.LENGTH_SHORT).show())
                              .onRender((nbPages, pageWidth, pageHeight) -> pdfView.fitToWidth())
                              .enableAnnotationRendering(true)
                              .invalidPageColor(Color.WHITE)
                              .load();

                  }
                  else if (viewType.equals("storage")){
                      Uri pdfFile = Uri.parse(getIntent().getStringExtra("FileUri"));

                      pdfView.fromUri(pdfFile)
                              .enableSwipe(true)
                              .swipeHorizontal(false)
                              .enableDoubletap(true)
                              .onPageError(new OnPageErrorListener() {
                                  @Override
                                  public void onPageError(int page, Throwable t) {
                                      Toast.makeText(ViewActivity.this,"Error while opening"+page,Toast.LENGTH_SHORT).show();
                                  }
                              })
                              .onRender((nbPages, pageWidth, pageHeight) -> pdfView.fitToWidth())
                              .enableAnnotationRendering(true)
                              .invalidPageColor(Color.WHITE)
                              .load();

                  }
                   else if(viewType.equals("internet")){
                     progressBar.setVisibility(View.VISIBLE);
                      FileLoader.with(this)
                              .load("http://www.africau.edu/images/default/sample.pdf")
                              .fromDirectory("PDFFiles",FileLoader.DIR_INTERNAL)
                              .asFile(new FileRequestListener<File>() {
                                  @Override
                                  public void onLoad(FileLoadRequest request, FileResponse<File> response) {

                                      progressBar.setVisibility(View.GONE);

                                     File pdfFile = response.getBody();

                                      pdfView.fromFile(pdfFile)
                                              .enableSwipe(true)
                                              .swipeHorizontal(false)
                                              .enableDoubletap(true)
                                              .onPageError((page, t) -> Toast.makeText(ViewActivity.this,"Error while opening"+page,Toast.LENGTH_SHORT).show())
                                              .onRender((nbPages, pageWidth, pageHeight) -> pdfView.fitToWidth())
                                              .enableAnnotationRendering(true)
                                              .invalidPageColor(Color.WHITE)
                                              .load();

                                  }

                                  @Override
                                  public void onError(FileLoadRequest request, Throwable t) {
                                       Toast.makeText(ViewActivity.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.GONE);
                                  }
                              });

                  }


             }

        }
    }
}