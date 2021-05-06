package com.jmarser.proyecto_as.generarPDF.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.generarPDF.interactor.GenerarPDFInteractorImpl;
import com.jmarser.proyecto_as.generarPDF.presenter.GenerarPDFPresenter;
import com.jmarser.proyecto_as.generarPDF.presenter.GenerarPDFPresenterImpl;
import com.jmarser.proyecto_as.model.Alumno;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import es.dmoral.toasty.Toasty;


public class GenerarPDFFragment extends Fragment implements GenerarPDFView {

    private GenerarPDFPresenter presenter;
    private Alumno alumno = null;

    public GenerarPDFFragment() {
        // Required empty public constructor
    }

    public static GenerarPDFFragment newInstance() {
        GenerarPDFFragment fragment = new GenerarPDFFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generar_pdf, container, false);

        presenter = new GenerarPDFPresenterImpl(getContext(), this, new GenerarPDFInteractorImpl(getContext()));
        presenter.getAlumno();

        return view;
    }


    @Override
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;

        try {
            crearPdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toasty.error(getContext(), "Error al crear el PDF", Toasty.LENGTH_SHORT).show();
        }

    }

    @Override
    public void errorAlumno(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void serverError(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void userWithoutAuthorization(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    private void crearPdf() throws FileNotFoundException {
        //establecemos donde se va a crear el pdf.
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        //creamos el archivo
        File file = new File(pdfPath, "Fichas FCT.pdf");

        //indicamos que vamos a escribir en el documento y que este es un pdf.
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        //procesamos la imagen de uno de los logos
        Drawable logo1 = getActivity().getDrawable(R.drawable.logo_junta);
        Bitmap bitmap1 = ((BitmapDrawable) logo1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitmapTabla1 = stream1.toByteArray();

        ImageData imagen1 = ImageDataFactory.create(bitmapTabla1);
        Image image1 = new Image(imagen1);
        image1.setHeight(50);

        //Procesamos la imagen de otro de los logos
        Drawable logo2 = getActivity().getDrawable(R.drawable.logo_ies);
        Bitmap bitmap2 = ((BitmapDrawable) logo2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapTabla2 = stream2.toByteArray();

        ImageData imagen2 = ImageDataFactory.create(bitmapTabla2);
        Image image2 = new Image(imagen2);
        image2.setHeight(50);

        //color para la cabecera de las fichas
        DeviceRgb cabeceraGris = new DeviceRgb(200, 240, 200);

        int k = 0; // variable con la contaremos el ciclo para mostrar las fichas.

        /*Como queremos mostrar 5 fichas por hoja, obtenemos el número total de hojas que va a tener
        * el documento dividiendo el total de fichas y le sumamos uno para compensar los decimales*/
        int numHojas = (this.alumno.getFichas().size() / 5) + 1;

        /*Creamos un bucle para generar las hojas que va a tener nuestro documento*/
        for (int i = 0; i < numHojas; i++) {
            /*Primera tabla, mostrara los logos de la junta de andalucia y el del instituto, además
            * de un texto*/
            float columnWidth_table1[] = {90, 370, 100}; //indicamos cuantas columnas va a tener la tabla
            Table table1 = new Table(columnWidth_table1);

            //tupla 01
            table1.addCell(new Cell(3, 1).add(image1).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell(3, 1).add(image2).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
            //tupla 02
            //table1.addCell(new Cell().add(new Paragraph("")));//se anula por el rowspan
            table1.addCell(new Cell().add(new Paragraph("").setBold().setTextAlignment(TextAlignment.CENTER)).setFontSize(10f).setBorder(Border.NO_BORDER));
            //table1.addCell(new Cell().add(new Paragraph("")));//se anula por el rowspan
            //tupla 03
            //table1.addCell(new Cell().add(new Paragraph("")));//se anula por el rowspan
            table1.addCell(new Cell().add(new Paragraph("FORMACIÓN EN CENTROS DE TRABAJO FICHA SEMANAL").setBold().setTextAlignment(TextAlignment.CENTER)).setFontSize(10f).setVerticalAlignment(VerticalAlignment.BOTTOM).setBorder(Border.NO_BORDER));
            //table1.addCell(new Cell().add(new Paragraph("")));//se anula por el rowspan

            /*Segunda tabla, contiene la información de quien, donde y quién a supervisado las prácticas*/
            float columnWidth_table2[] = {110, 160, 120, 170};
            Table table2 = new Table(columnWidth_table2);

            //tupla 01
            table2.addCell(new Cell().add(new Paragraph("Centro docente:").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph("I.E.S. Trassierra").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph("Centro de trabajo:").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph(this.alumno.getTutor().getEmpresa()).setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            //tupla 02
            table2.addCell(new Cell().add(new Paragraph("Profesor responsable:").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph(this.alumno.getProfesor().toString()).setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph("Tutor centro de trabajo:").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph(this.alumno.getTutor().toString()).setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            //tupla 03
            table2.addCell(new Cell().add(new Paragraph("Alumno/a:").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph(this.alumno.toString()).setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph("Ciclo formativo:").setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table2.addCell(new Cell().add(new Paragraph(this.alumno.getCiclo()).setFontSize(10f)).setVerticalAlignment(VerticalAlignment.MIDDLE));

            /*Tercera tabla*/
            float columnWidth_table3[] = {60, 40, 280, 180};
            Table table3 = new Table(columnWidth_table3);

            //tupla 01
            table3.addCell(new Cell().add(new Paragraph("FECHA").setFontSize(10f).setTextAlignment(TextAlignment.CENTER).setBold()).setBackgroundColor(cabeceraGris));
            table3.addCell(new Cell().add(new Paragraph("HORAS").setFontSize(10f).setTextAlignment(TextAlignment.CENTER).setBold()).setBackgroundColor(cabeceraGris));
            table3.addCell(new Cell().add(new Paragraph("DESCRIPCIÓN").setFontSize(10f).setTextAlignment(TextAlignment.CENTER).setBold()).setBackgroundColor(cabeceraGris));
            table3.addCell(new Cell().add(new Paragraph("OBSERVACIONES").setFontSize(10f).setTextAlignment(TextAlignment.CENTER).setBold()).setBackgroundColor(cabeceraGris));

            /*Creamos un bucle para escribir las fichas, utilizamos la variable K para iniciar la
            * cuenta, esta variable la aumentaremos al terminar cada bucle en 5 con lo que sacaremos
            * las fichas en orden y sin repetir.*/
            for (int j = k; j < k + 5; j++) {
                if(j < this.alumno.getFichas().size()) {
                    table3.addCell(new Cell().add(new Paragraph("\n\n\n\n"+this.alumno.getFichas().get(j).getFecha()+"\n\n\n").setTextAlignment(TextAlignment.CENTER).setFontSize(8f)));
                    table3.addCell(new Cell().add(new Paragraph("\n\n\n\n" + this.alumno.getFichas().get(j).getHoras() + "\n\n\n").setTextAlignment(TextAlignment.CENTER).setFontSize(8f)));
                    table3.addCell(new Cell().add(new Paragraph(this.alumno.getFichas().get(j).getDescripcion()).setFontSize(8f)));
                    table3.addCell(new Cell().add(new Paragraph(this.alumno.getFichas().get(j).getObservaciones()).setFontSize(8f)));
                }else{
                    table3.addCell(new Cell().add(new Paragraph("\n\n\n\n\n\n\n").setTextAlignment(TextAlignment.CENTER).setFontSize(8f)));
                    table3.addCell(new Cell().add(new Paragraph("\n\n\n\n\n\n\n").setTextAlignment(TextAlignment.CENTER).setFontSize(8f)));
                    table3.addCell(new Cell().add(new Paragraph("").setFontSize(8f)));
                    table3.addCell(new Cell().add(new Paragraph("").setFontSize(8f)));
                }
            }
            k += 5;

            /*Cuarta tabla, mostrará las líneas de firmas*/
            float columnWidth_table4[] = {200, 180, 40, 180};
            Table table4 = new Table(columnWidth_table4);
            //tupla 01
            table4.addCell(new Cell().add(new Paragraph("El/La Alumno/a:").setBold().setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("El/La Profesor/a responsable:").setBold().setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("\n").setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("El/la Tutor/a:").setBold().setFontSize(10f)).setBorder(Border.NO_BORDER));
            //tupla 02
            table4.addCell(new Cell().add(new Paragraph(this.alumno.toString()).setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph(this.alumno.getProfesor().toString()).setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("\n").setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph(this.alumno.getTutor().toString()).setFontSize(10f)).setBorder(Border.NO_BORDER));
            //tupla 03
            table4.addCell(new Cell().add(new Paragraph("Fdo:___________________").setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("Fdo:___________________").setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("\n").setFontSize(10f)).setBorder(Border.NO_BORDER));
            table4.addCell(new Cell().add(new Paragraph("Fdo:___________________").setFontSize(10f)).setBorder(Border.NO_BORDER));

            /*Agregamos las diferentes tablas al documento y para separarlas les añadimos un intro*/
            document.add(table1);
            document.add(new Paragraph("\n"));
            document.add(table2);
            document.add(new Paragraph("\n"));
            document.add(table3);
            document.add(new Paragraph("\n"));
            document.add(table4);
            document.add(new Paragraph("\nHoja " + (i + 1) + "\n").setTextAlignment(TextAlignment.RIGHT).setFontSize(10f));
        }

        //Cerramos el documento.
        document.close();

        Toasty.success(getContext(), "PDf creado correctamente", Toasty.LENGTH_SHORT).show();
    }
}