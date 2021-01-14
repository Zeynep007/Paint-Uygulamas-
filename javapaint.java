import javax.swing.*;   //gerekli bileþenleri kullanabilmek için kütüphanemizi ekledik
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;  //þekiller çizerken týkldýðýmýzda koordinatlarý belirtmek için
import java.util.*;
import java.text.DecimalFormat;


public class javapaint  extends JFrame  {
	
	JButton  butFirca ,butCizgi , butElips,butDikdortgen ,butKenarlik ,butDolgu;   //buton deðiþkenlerimi tanýmladým
	JSlider transSlider;
	JLabel transLabel;
	
	DecimalFormat dec = new DecimalFormat("#.##") ;
	
	Graphics2D grafikAyar;
	
	int sýra =1;  //mause ile neler yaptýðýmýzý saymasý için deðiþken tanýmladýk
	
	float transparantValue = 1.0f;
	
	Color kenarRenk = Color.black , dolguRenk = Color.black;   //kenarlýk  ve dolgu rengi vermeye çalýþtým
	
	public static void main (String[] args) {
		new  javapaint();
		
	}
	public javapaint() {
		this.setSize(1000, 1000);   //ekranýmýzun boyutlarýný verdik
		this.setTitle("JAVA PAÝNT UYGULAMAM");  //ekranýmýza baþlýk verdik
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //ekranýmýzdan çarpý iþareti ile çýkmasýný kodladýk
		JPanel buttonPanel = new JPanel();  //butonlar için özel panelimizi tanýmladýk
		Box kutu = Box.createHorizontalBox();  //yatay kutu tanýmladýk
		
		butFirca = butonyap("C:/Users/hp/Desktop/grafik/firca.jpg",1);  //butonlarýmýzýn fonksiyonlarýný ve simgelerini ekledik
		butCizgi = butonyap("C://Users//hp//Desktop//grafik//cizgi.jpg/" , 2);
		butElips = butonyap("C:/Users/hp/Desktop/grafik/elips.jpg",3);
		butDikdortgen = butonyap("C:/Users/hp/Desktop/grafik/dikdortgen.png",4);
		
		butKenarlik = renklibutonyap("C:/Users/hp/Desktop/grafik/kenarlik.png",5,true);
		butDolgu = renklibutonyap("C:/Users/hp/Desktop/grafik/dolgu.png",6,false);
		
		transLabel =new JLabel("TRANSPARAN 1");
		transSlider = new JSlider(1 ,99,99);
		ListenForSlider sliderL = new ListenForSlider();
		
		transSlider.addChangeListener(sliderL);
		kutu.add(transLabel);
		kutu.add(transSlider);
		
		
		kutu.add(butFirca);   
		kutu.add(butCizgi);
		kutu.add(butElips);
		kutu.add(butDikdortgen);
		kutu.add(butKenarlik);
		kutu.add(butDolgu);
		
		buttonPanel.add(kutu); //butonlarýmýzý panelimizin içerisine ekledik
		
		this.add(buttonPanel , BorderLayout.SOUTH); //panelimizi frame a ekledik  panelimizin konumunu güney kýsmýna ekledik
		this.add(new cizimTahtasi(),BorderLayout.CENTER); //framemizin ortasýna cizim tahtasý ekledik
		this.setVisible(true); //farmemizin yani ekranýmýzýn görünür olmasýný saðladýk
		
		}//constructor sonu (java paint())
	
	   public JButton butonyap (String iconDosya , final int sýraSayisi ) {
		   JButton but = new JButton();
		   Icon butIcon = new ImageIcon(iconDosya);
		   but.setIcon(butIcon);
		   
		   
		   but.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
			  sýra =  sýraSayisi; // týklanýnca gelecek sýra
			  
			  
				
			}
		});
		   return but;
		   // butonyap sonu
	   }
		   
		   public JButton renklibutonyap (String iconDosya , final int sýraSayisi ,final boolean kenarlik ) {
			   JButton but = new JButton();
			   Icon butIcon = new ImageIcon(iconDosya);
			   but.setIcon(butIcon);
			   
			   
			   but.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
				  if (kenarlik) {
					  kenarRenk = JColorChooser.showDialog(null, "Kenarlik rengi seçiniz!", Color.black);  
					
				}else {
					dolguRenk = JColorChooser.showDialog(null, "Dolgu rengi seçiniz!", Color.black);  
				}
				}
			});
			   return but;   
		   }
		   private class cizimTahtasi extends JComponent{
			   ArrayList<Shape> sekiller = new ArrayList<Shape>();
			   ArrayList<Color> dolgusekli = new  ArrayList<Color>();
			   ArrayList<Color> kenarliksekli = new  ArrayList<Color>();
			   ArrayList<Float> transYüzdesi = new ArrayList<Float>();
			   
			   
			   Point cizimbaslangic , cizimson;
			   
			   
			   public cizimTahtasi() {
				   this.addMouseListener(new MouseAdapter() {
					  
					   public void mousePressed(MouseEvent e) {
						   if(sýra != 1) {
						   cizimbaslangic = new Point(e.getX() , e.getY());
						   cizimson = cizimbaslangic;
						   repaint();
						   }
					   }
					   public void MouseReleased(MouseEvent e){
						   if(sýra != 1) {
							   Shape sekil =null;
							   
							   if(sýra == 2) {
								   sekil =cizgiciz( cizimbaslangic.x , cizimbaslangic.y , e.getX() , e.getY());  
								   
							   }else if(sýra == 3) {
								   sekil =elipsciz( cizimbaslangic.x , cizimbaslangic.y , e.getX() , e.getY());  
							   }else if(sýra == 4) {
								   sekil =dikdortgenciz( cizimbaslangic.x , cizimbaslangic.y , e.getX() , e.getY());
							   }
						   sekiller.add(sekil);
						   dolgusekli.add(dolguRenk);
						   kenarliksekli.add(kenarRenk);
						   
						   
						   transYüzdesi.add(transparantValue);
						   
						   
						   cizimbaslangic=null;
						   cizimson=null;
						   repaint();
					   }
					   }
			  }); //mouselistener sonu
				   this.addMouseMotionListener(new MouseMotionAdapter() {
					
					
					public void mouseDragged(MouseEvent e) {
						if(sýra == 1) {
						int x=e.getX();
						int y=e.getY();
						
						Shape sekil =null;
						kenarRenk=dolguRenk;
						
						sekil=fircaCiz(x,y,5,5);
						
						sekiller.add(sekil);	
						
						dolgusekli.add(dolguRenk);
						kenarliksekli.add(kenarRenk);
						transYüzdesi.add(transparantValue);
					}
					cizimson = new Point(e.getX(),e.getY());
						repaint();
						
					}
				}); ///mouse moution sonu
			   }
			   
			   public void paint(Graphics g) {
				   grafikAyar = (Graphics2D)g;
				   grafikAyar.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				   grafikAyar.setStroke(new BasicStroke(4));
				   
				   Iterator<Color> kenarliksay = kenarliksekli.iterator();
				   Iterator<Color> dolgusay = dolgusekli.iterator();
				   Iterator<Float> transSay = transYüzdesi.iterator();
				   
				   
				   
				   
				   for (Shape s: sekiller ) {
					   grafikAyar.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transSay.next()));
					   
					   grafikAyar.setPaint(kenarliksay.next());
					   grafikAyar.draw(s);
					   grafikAyar.setPaint(dolgusay.next());
					   grafikAyar.fill(s);
					   
				}
				   if(cizimbaslangic != null && cizimson !=null) {
					   grafikAyar.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f)); //siyah renk 0,4
					   grafikAyar.setPaint(Color.LIGHT_GRAY);
					   
					   Shape sekil=null;
					   if (sýra ==2) {
						   sekil= cizgiciz(cizimbaslangic.x,cizimbaslangic.y,cizimson.x,cizimson.y);
						
					}else if(sýra  == 3) {
						sekil= elipsciz(cizimbaslangic.x,cizimbaslangic.y,cizimson.x,cizimson.y);
					}else if(sýra==4) {
						sekil= dikdortgenciz(cizimbaslangic.x,cizimbaslangic.y,cizimson.x,cizimson.y);
					}
					   grafikAyar.draw(sekil);
					   
					    
					   
					}				   
			   }		   
		   
		   private Rectangle2D.Float dikdortgenciz(int x1 ,int y1 ,int x2 ,int y2){
			   int x = Math.min(x1, x2);
			   int y = Math.min(y1, y2);
			   
			   int genislik =Math.abs(x1-x2);
			   int yukseklik=Math.abs(y1-y2);
			   
			   return new Rectangle2D.Float (x,y,genislik,yukseklik);			   
		   }
		   private Ellipse2D.Float elipsciz(int x1 ,int y1 ,int x2 ,int y2){
			   int x = Math.min(x1, x2);
			   int y = Math.min(y1, y2);
			   
			   int genislik =Math.abs(x1-x2);
			   int yukseklik=Math.abs(y1-y2);
			   
			   return new Ellipse2D.Float (x,y,genislik,yukseklik);	
		   }
		   private Line2D.Float cizgiciz(int x1 ,int y1 ,int x2 ,int y2){
			   return new Line2D.Float(x1,y1,x2,y2);
			   
		   }
		   private Ellipse2D.Float fircaCiz(int x1 ,int y1 , int fircaKenarlikGenisligi , int fircaKenarlikYuksekligi){
			   return new Ellipse2D.Float(x1,y1,fircaKenarlikGenisligi,fircaKenarlikYuksekligi);
		   }
}//cizimtahtasi son
		   private class ListenForSlider implements ChangeListener{

			
			public void stateChanged(ChangeEvent e) {
				if(e.getSource() == transSlider) {
					transLabel.setText("Transparan"+dec.format(transSlider.getValue() * .01));
					
				}
				 float transparantVal = (float) (transSlider.getValue() * .01);
				
				
				
			}
			   
			   
		   }
		   
		   
		   
	   }
	
