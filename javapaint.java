import javax.swing.*;   //gerekli bile�enleri kullanabilmek i�in k�t�phanemizi ekledik
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;  //�ekiller �izerken t�kld���m�zda koordinatlar� belirtmek i�in
import java.util.*;
import java.text.DecimalFormat;


public class javapaint  extends JFrame  {
	
	JButton  butFirca ,butCizgi , butElips,butDikdortgen ,butKenarlik ,butDolgu;   //buton de�i�kenlerimi tan�mlad�m
	JSlider transSlider;
	JLabel transLabel;
	
	DecimalFormat dec = new DecimalFormat("#.##") ;
	
	Graphics2D grafikAyar;
	
	int s�ra =1;  //mause ile neler yapt���m�z� saymas� i�in de�i�ken tan�mlad�k
	
	float transparantValue = 1.0f;
	
	Color kenarRenk = Color.black , dolguRenk = Color.black;   //kenarl�k  ve dolgu rengi vermeye �al��t�m
	
	public static void main (String[] args) {
		new  javapaint();
		
	}
	public javapaint() {
		this.setSize(1000, 1000);   //ekran�m�zun boyutlar�n� verdik
		this.setTitle("JAVA PA�NT UYGULAMAM");  //ekran�m�za ba�l�k verdik
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //ekran�m�zdan �arp� i�areti ile ��kmas�n� kodlad�k
		JPanel buttonPanel = new JPanel();  //butonlar i�in �zel panelimizi tan�mlad�k
		Box kutu = Box.createHorizontalBox();  //yatay kutu tan�mlad�k
		
		butFirca = butonyap("C:/Users/hp/Desktop/grafik/firca.jpg",1);  //butonlar�m�z�n fonksiyonlar�n� ve simgelerini ekledik
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
		
		buttonPanel.add(kutu); //butonlar�m�z� panelimizin i�erisine ekledik
		
		this.add(buttonPanel , BorderLayout.SOUTH); //panelimizi frame a ekledik  panelimizin konumunu g�ney k�sm�na ekledik
		this.add(new cizimTahtasi(),BorderLayout.CENTER); //framemizin ortas�na cizim tahtas� ekledik
		this.setVisible(true); //farmemizin yani ekran�m�z�n g�r�n�r olmas�n� sa�lad�k
		
		}//constructor sonu (java paint())
	
	   public JButton butonyap (String iconDosya , final int s�raSayisi ) {
		   JButton but = new JButton();
		   Icon butIcon = new ImageIcon(iconDosya);
		   but.setIcon(butIcon);
		   
		   
		   but.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
			  s�ra =  s�raSayisi; // t�klan�nca gelecek s�ra
			  
			  
				
			}
		});
		   return but;
		   // butonyap sonu
	   }
		   
		   public JButton renklibutonyap (String iconDosya , final int s�raSayisi ,final boolean kenarlik ) {
			   JButton but = new JButton();
			   Icon butIcon = new ImageIcon(iconDosya);
			   but.setIcon(butIcon);
			   
			   
			   but.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
				  if (kenarlik) {
					  kenarRenk = JColorChooser.showDialog(null, "Kenarlik rengi se�iniz!", Color.black);  
					
				}else {
					dolguRenk = JColorChooser.showDialog(null, "Dolgu rengi se�iniz!", Color.black);  
				}
				}
			});
			   return but;   
		   }
		   private class cizimTahtasi extends JComponent{
			   ArrayList<Shape> sekiller = new ArrayList<Shape>();
			   ArrayList<Color> dolgusekli = new  ArrayList<Color>();
			   ArrayList<Color> kenarliksekli = new  ArrayList<Color>();
			   ArrayList<Float> transY�zdesi = new ArrayList<Float>();
			   
			   
			   Point cizimbaslangic , cizimson;
			   
			   
			   public cizimTahtasi() {
				   this.addMouseListener(new MouseAdapter() {
					  
					   public void mousePressed(MouseEvent e) {
						   if(s�ra != 1) {
						   cizimbaslangic = new Point(e.getX() , e.getY());
						   cizimson = cizimbaslangic;
						   repaint();
						   }
					   }
					   public void MouseReleased(MouseEvent e){
						   if(s�ra != 1) {
							   Shape sekil =null;
							   
							   if(s�ra == 2) {
								   sekil =cizgiciz( cizimbaslangic.x , cizimbaslangic.y , e.getX() , e.getY());  
								   
							   }else if(s�ra == 3) {
								   sekil =elipsciz( cizimbaslangic.x , cizimbaslangic.y , e.getX() , e.getY());  
							   }else if(s�ra == 4) {
								   sekil =dikdortgenciz( cizimbaslangic.x , cizimbaslangic.y , e.getX() , e.getY());
							   }
						   sekiller.add(sekil);
						   dolgusekli.add(dolguRenk);
						   kenarliksekli.add(kenarRenk);
						   
						   
						   transY�zdesi.add(transparantValue);
						   
						   
						   cizimbaslangic=null;
						   cizimson=null;
						   repaint();
					   }
					   }
			  }); //mouselistener sonu
				   this.addMouseMotionListener(new MouseMotionAdapter() {
					
					
					public void mouseDragged(MouseEvent e) {
						if(s�ra == 1) {
						int x=e.getX();
						int y=e.getY();
						
						Shape sekil =null;
						kenarRenk=dolguRenk;
						
						sekil=fircaCiz(x,y,5,5);
						
						sekiller.add(sekil);	
						
						dolgusekli.add(dolguRenk);
						kenarliksekli.add(kenarRenk);
						transY�zdesi.add(transparantValue);
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
				   Iterator<Float> transSay = transY�zdesi.iterator();
				   
				   
				   
				   
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
					   if (s�ra ==2) {
						   sekil= cizgiciz(cizimbaslangic.x,cizimbaslangic.y,cizimson.x,cizimson.y);
						
					}else if(s�ra  == 3) {
						sekil= elipsciz(cizimbaslangic.x,cizimbaslangic.y,cizimson.x,cizimson.y);
					}else if(s�ra==4) {
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
	
