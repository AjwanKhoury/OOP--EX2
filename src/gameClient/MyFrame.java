package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.imageio.ImageIO;
import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 *
 */
public class MyFrame extends JFrame{
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private Ex2_Client ex2Client;
	private int scenario_num = -1;

	Image pc= new ImageIcon("Poke_Ball.png").getImage();
	Image ts= new ImageIcon("Lincoln's_Pikachu.png").getImage();


	MyFrame(int scenario_num) {
		super("OOP-Ex2   Ajwan & Ahmad");
		this.scenario_num = scenario_num;
		try {
			init_to_gui();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void init_to_gui() throws IOException{
		MenuBar MB = new MenuBar();
		Menu file = new Menu("File");//add the file list to the menu bar
		MenuItem load = new MenuItem("Load Game");
		MenuItem save = new MenuItem("Save Game");
		MenuItem exit = new MenuItem("Exit");
		file.add(load);
		file.add(save);
		file.add(exit);
		MB.add(file);

		Menu game = new Menu("Game");
		MenuItem start = new MenuItem("Start Game");
		MenuItem stop = new MenuItem("Stop Game");
		MenuItem add = new MenuItem("Add Agent");
		game.add(start);
		game.add(stop);
		game.add(add);
		MB.add(game);

		setMenuBar(MB);

		exit.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		load.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JFrame Jframe2= new JFrame();
				Jframe2.setTitle("Inset game");
				Jframe2.setLocation(400, 240);
				Jframe2.setSize(270 , 120); 
				Jframe2.setVisible(true);
				Container container = Jframe2.getContentPane();
				container.setLayout(new FlowLayout());
				JTextField textField = new JTextField();
				textField.setPreferredSize(new Dimension(150, 25));
				JLabel label = new JLabel("Input scenario num and \"OK\"");

				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String input = textField.getText();
						loadGame( Integer.parseInt(input));
						Jframe2.dispose();
					}
				});

				container.add(textField);
				container.add(okButton);
				container.add(label);
			}
		});
		save.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JFrame Jframe2= new JFrame();
				Jframe2.setTitle("Inset game");
				Jframe2.setLocation(400, 240);
				Jframe2.setSize(270 , 120); 
				Jframe2.setVisible(true);
				Container container = Jframe2.getContentPane();
				container.setLayout(new FlowLayout());
				JTextField textField = new JTextField();
				textField.setPreferredSize(new Dimension(150, 25));
				JLabel label = new JLabel("Input the saving name file and \"OK\"");

				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String input = textField.getText();
						saveGame(input);
						Jframe2.dispose();
					}
				});

				container.add(textField);
				container.add(okButton);
				container.add(label);
			}
		});
		start.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startGame();
			}
		});
		stop.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stopGame();
			}
		});
		add.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JFrame Jframe2= new JFrame();
				Jframe2.setTitle("Inset game");
				Jframe2.setLocation(400, 240);
				Jframe2.setSize(270 , 120); 
				Jframe2.setVisible(true);
				Container container = Jframe2.getContentPane();
				container.setLayout(new FlowLayout());
				JTextField textField = new JTextField();
				textField.setPreferredSize(new Dimension(150, 25));
				JLabel label = new JLabel("Input the location of Agent and \"OK\"");

				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String input = textField.getText();
						addAgent(Integer.parseInt(input));
						Jframe2.dispose();
					}
				});

				container.add(textField);
				container.add(okButton);
				container.add(label);
			}
		});
	}

	private void loadGame(int num) {  
		this.scenario_num = num;
		this.ex2Client.setGame(num);
		this.dispose();
	}

	private void saveGame(String input) {
		this.ex2Client.savingGame(input+".json");	
	}

	private void startGame() {	
		this.ex2Client.getGame().startGame();
		this.ex2Client.nothing();
	}

	private void stopGame() {
		this.ex2Client.getGame().stopGame();
	}

	private void addAgent(int id) {
		this.ex2Client.getGame().addAgent(id);
		this.ex2Client.nothing();
	}

	public void gameUpdate(Ex2_Client game) {
		this.ex2Client = game;
	}

	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}

	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	}

	public void paint(Graphics g) {
		this.add(new MyPanel(this._w2f));
		this.revalidate();
	}


	private class MyPanel extends JPanel{

		private Range2Range _w2f;


		public MyPanel(Range2Range range) {
			if(range != null)
				this._w2f = range ;
			this.revalidate();
		}

		public void paintComponent(Graphics g) {
			Graphics2D gg = (Graphics2D) g;
			super.paintComponent(gg);

			BufferedImage img = null;
			ImageObserver observer = null;
			try { 
				img = ImageIO.read(new File("packground.jpg"));
			} 
			catch (IOException e) {
				System.out.println("Drawing Error");
			}

			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), observer);


			Range rx = new Range(20,this.getWidth()-20);
			Range ry = new Range(this.getHeight()-10,30);
			Range2D frame = new Range2D(rx,ry);
			directed_weighted_graph gggg = _ar.getGraph();
			_w2f = Arena.w2f(gggg,frame);

			if(_ar != null && _w2f != null) {
				drawGraph(gg);
				drawPokemons(gg);
				drawAgants(gg);
				drawInfo(gg);
				draw_scoreText(gg);
			}

			this.revalidate();
			this.setVisible(true);
		}


		public void draw_scoreText(Graphics g) {
			g.setColor(Color.white);
			g.drawString("Scenario Number: " + scenario_num, 10, 20);
			g.drawString("Remaining time:  " +ex2Client.getGame().timeToEnd(), 10, 40);
			List<CL_Agent> agents_list = _ar.getAgents();
			int grade = 0;
			for(int agent_id=0;agents_list != null && agent_id < agents_list.size();agent_id++) {
				grade += agents_list.get(agent_id).getValue();
			}
			g.drawString("Times Remaining:  " + (int)ex2Client.getGame().timeToEnd()/1000, 10, 60);
			g.drawString("Total score: " + grade, 10, 80);
		}

		private void drawInfo(Graphics g) {
			List<String> str = _ar.get_info();
			String dt = "none";
			for(int i=0;i<str.size();i++) {
				System.out.println("you want to print  " + str.get(i));
				g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
			}
		}

		private void drawGraph(Graphics g) {
			directed_weighted_graph gg = _ar.getGraph();
			Iterator<node_data> iter = gg.getV().iterator();
			while(iter.hasNext()) {
				node_data n = iter.next();
				g.setColor(Color.blue);
				drawNode(n,5,g);
				Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
				while(itr.hasNext()) {
					edge_data e = itr.next();
					g.setColor(Color.gray);
					drawEdge(e, g);
				}
			}
		}

		private void drawPokemons(Graphics g) {
			List<CL_Pokemon> fs = _ar.getPokemons();
			if(fs!=null) {
				Iterator<CL_Pokemon> itr = fs.iterator();

				while(itr.hasNext()) {

					CL_Pokemon f = itr.next();
					Point3D c = f.getLocation();
					int r=10;
					g.setColor(Color.green);
					if(f.getType()<0) {g.setColor(Color.orange);}
					if(c!=null) {

						geo_location fp = this._w2f.world2frame(c);
						g.drawImage(pc,(int)fp.x()-r, (int)fp.y()-r, 4*r, 4*r , this);
					}
				}
			}
		}

		private void drawAgants(Graphics g) {
			List<CL_Agent> rs = _ar.getAgents();

			g.setColor(Color.red);
			int i=0;
			while(rs!=null && i<rs.size()) {
				geo_location c = rs.get(i).getLocation();
				int r=8;
				i++;
				if(c!=null) {

					geo_location fp = this._w2f.world2frame(c);
					g.drawImage(ts,(int)fp.x()-r, (int)fp.y()-r, 9*r, 9*r , this);

				}
			}
		}

		private void drawNode(node_data n, int r, Graphics g) {

			geo_location fp = this._w2f.world2frame( n.getLocation());
			g.fillOval((int) fp.x() - 5, (int) fp.y() - 5, 7, 7);
			g.setFont(new Font("David", Font.PLAIN, 15));
			g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 2 * 5);
		}

		private void drawEdge(edge_data e, Graphics g) {
			directed_weighted_graph gg = _ar.getGraph();
			geo_location s = gg.getNode(e.getSrc()).getLocation();
			geo_location d = gg.getNode(e.getDest()).getLocation();
			geo_location s0 = this._w2f.world2frame(s);
			geo_location d0 = this._w2f.world2frame(d);
			g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
		}

	}
}








