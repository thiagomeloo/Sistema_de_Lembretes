package View;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import sun.security.pkcs11.wrapper.Constants;

/**
 *
 * @author Thiago Melo
 *      Skype thiago.melo455
 *      email 4thiagomelo5@gmail.com
 * 
 */

public class Principal extends javax.swing.JFrame {

    DefaultListModel modelListaTag = new DefaultListModel();
    DefaultListModel modelListaItem = new DefaultListModel();
    DefaultListModel modelListaItemTEMP = new DefaultListModel();
    DefaultListModel modelListaTagTEMP = new DefaultListModel();
    
    String tagAtual;
    String itemSelecionado;
    String itemTEMP;
    int contC = 0;
    String tagSelecionada;
    int posFrameX;
    int posFrameY;
    
    public Principal() {
        initComponents();
        
        for (int i = 1; i <= 5; i++) {
            lerOArquivo(i); //executa as 5 funções no inicio do programa
        }
        
        //centraliza os itens da lista
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)listaTag.getCellRenderer();  
        renderer.setHorizontalAlignment(new JLabel().CENTER);  
        listaTag.setCellRenderer(renderer);
        listaItem.setCellRenderer(renderer);
        
        itemAdd.setLineWrap(true);//para ajustar o textArea
        
        listaItem.setModel(modelListaItem);
        
        tagAtual = "";
    }

    public void popularCaixaDeTags(String item){
        tagSelected.addItem(item);
    }
    public void limparArquivo(int value){
        //se valor for 1 deleta o item
        if(value == 1){
            File file = new File("item.txt");
            if ( file.exists()) {
               file.delete();
            } 
        }else if (value == 2){ // se o valor for 2 deleta as tag
            File file = new File("tag.txt");
            if ( file.exists()) {
               file.delete();
            } 
        }
        
    }
    

    public void escreverNoArquivo(String msg, int value){
        if (value == 1) {
            File arquivo = new File("tag.txt");
            try {
                FileWriter escrita = new FileWriter(arquivo, true);
                PrintWriter saida = new PrintWriter(escrita);
                //saida.write(msg);
                saida.append(Constants.NEWLINE + msg); //escreve uma nova linha no fim do arquivo
                
                saida.close();
                escrita.close();
                
            } catch (IOException ex) {

            }
        }else if (value == 2){
            File arquivo = new File("item.txt");
            try {
                FileWriter escrita = new FileWriter(arquivo, true);
                PrintWriter saida = new PrintWriter(escrita);
                //saida.write(msg);
                saida.append(Constants.NEWLINE + msg + "    [" + tagAtual + "]"); //escreve uma nova linha no fim do arquivo
                saida.close();
                escrita.close();
            } catch (IOException ex) {

            }
            
        }

    }
    
    public void lerOArquivo(int value){
        try {
            //entrada for 1 ler o arquivo tag 
            if (value == 1) {
                FileReader leitor = new FileReader("tag.txt");
                BufferedReader br = new BufferedReader(leitor);
                String s = "";
                modelListaTag.clear();//limpa a lista
                tagSelected.removeAllItems();
                
                while ((s = br.readLine()) != null) { //percorre o arquivo aberto ate não existir mais linhas escritas
                    
                    popularCaixaDeTags(s);//add a tag no selected
                    
                    modelListaTag.addElement(s);  //add o item no model list
                    listaTag.setModel(modelListaTag); //atualiza a lista
                }
                
                leitor.close(); 
                br.close();
                
            //entrada for 2 filtra o arquivo por tag
            }else if(value == 2){
                
                FileReader leitor = new FileReader("item.txt");
                BufferedReader br = new BufferedReader(leitor);
                String s = "";
                modelListaItem.clear(); //limpa a lista 
                
                while((s = br.readLine()) != null ) { //percorre o arquivo aberto ate não existir mais linhas escritas
                    
                    int um;
                    int dois;
                    String x = "";
                    um = s.indexOf("[");
                    dois = s.indexOf("]");
                    
                    
                    try {
                       x = s.substring(um+1, dois);  //pega a tag referente ao item da vez  
                       
                    } catch (Exception e) {
                    }
                    if (x.equals(tagAtual)) {  //verifica se a tag e igual a da selecionada 
                        
                        modelListaItem.addElement(s); // adiciona a tag 
                        
                    }
                    
                    listaItem.setModel(modelListaItem); //atualiza a lista
                }
                leitor.close();
                br.close();
                
            //se entrada for 3 cria uma lista temporaria da tag para auxiliar na exclusão de tags
            }else if (value == 3){
                FileReader leitor = new FileReader("item.txt");
                BufferedReader br = new BufferedReader(leitor);
                String s = "";
                modelListaItemTEMP.clear();//limpa model da lista
                
                while((s = br.readLine()) != null ) { //percorre o arquivo aberto ate não existir mais linhas escritas
                    modelListaItemTEMP.addElement(s);
                }
                
                leitor.close();
                br.close();
            }
            
            //entrada for 4 seleciona todos os itens 
            else if (value == 4){
                FileReader leitor = new FileReader("item.txt");
                BufferedReader br = new BufferedReader(leitor);
                String s = "";
                modelListaItem.clear(); //limpa o model da lista
                while((s = br.readLine()) != null ) {  //percorre o arquivo aberto ate não existir mais linhas escritas
                    modelListaItem.addElement(s);
                    listaItem.setModel(modelListaItem);
                }
                
                leitor.close();
                br.close();
                
            //se entrada for 5 cria uma lista temporaria da tag para auxiliar na exclusão de tags
            }else if (value == 5){
                
                FileReader leitor = new FileReader("tag.txt");
                BufferedReader br = new BufferedReader(leitor);
                String s = "";
                modelListaTagTEMP.clear();//limpa a lista TEMP
                
                while((s = br.readLine()) != null ) { //percorre o arquivo aberto ate não existir mais linhas escritas
                    modelListaTagTEMP.addElement(s);
                }
                
                leitor.close();
                br.close();
            }

            
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex1) {
           
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitDefault = new javax.swing.JLabel();
        exitHover = new javax.swing.JLabel();
        Principal = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        painelLembretes = new javax.swing.JPanel();
        tagSelected = new javax.swing.JComboBox<>();
        scrolListItem = new javax.swing.JScrollPane();
        listaItem = new javax.swing.JList<>();
        lblSelecioneATag = new javax.swing.JLabel();
        scrollItemAdd = new javax.swing.JScrollPane();
        itemAdd = new javax.swing.JTextArea();
        btnRemoverTodosItens = new javax.swing.JLabel();
        btnRemoverItem = new javax.swing.JLabel();
        btnAdicionarItem = new javax.swing.JLabel();
        filtro = new javax.swing.JCheckBox();
        lblListaItens1 = new javax.swing.JLabel();
        painelTags = new javax.swing.JPanel();
        tagAdd = new javax.swing.JTextField();
        scrollListaTag = new javax.swing.JScrollPane();
        listaTag = new javax.swing.JList<>();
        lblListaTagsCadastradas = new javax.swing.JLabel();
        btnAdicionarTag = new javax.swing.JLabel();
        btnRemoverTodasTags = new javax.swing.JLabel();
        btnRemoverTag = new javax.swing.JLabel();
        painelSuperior = new javax.swing.JPanel();
        exit = new javax.swing.JLabel();
        lblListaItens = new javax.swing.JLabel();

        exitDefault.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Cancel_20px.png"))); // NOI18N

        exitHover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Cancel1_20px.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/Icons/Note_20px.png")).getImage());
        setUndecorated(true);

        Principal.setBackground(new java.awt.Color(255, 255, 255));

        tab.setBackground(new java.awt.Color(255, 255, 255));
        tab.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 51, 51)));
        tab.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tab.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tab.setOpaque(true);

        painelLembretes.setBackground(new java.awt.Color(255, 255, 255));
        painelLembretes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        painelLembretes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tagSelected.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tagSelected.setForeground(new java.awt.Color(255, 51, 51));
        tagSelected.setMaximumRowCount(100);
        tagSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagSelectedActionPerformed(evt);
            }
        });
        painelLembretes.add(tagSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 150, -1));

        listaItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        listaItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        listaItem.setForeground(new java.awt.Color(255, 51, 51));
        listaItem.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaItem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listaItem.setName(""); // NOI18N
        listaItem.setSelectionBackground(new java.awt.Color(255, 51, 51));
        listaItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaItemMouseClicked(evt);
            }
        });
        scrolListItem.setViewportView(listaItem);

        painelLembretes.add(scrolListItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 270, 190));

        lblSelecioneATag.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblSelecioneATag.setText("SELECIONE A TAG:");
        painelLembretes.add(lblSelecioneATag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 110, 20));

        itemAdd.setColumns(12);
        itemAdd.setRows(3);
        itemAdd.setWrapStyleWord(true);
        itemAdd.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));
        scrollItemAdd.setViewportView(itemAdd);

        painelLembretes.add(scrollItemAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 80));

        btnRemoverTodosItens.setBackground(new java.awt.Color(255, 255, 255));
        btnRemoverTodosItens.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRemoverTodosItens.setForeground(new java.awt.Color(255, 51, 51));
        btnRemoverTodosItens.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRemoverTodosItens.setText("REMOVER TUDO");
        btnRemoverTodosItens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        btnRemoverTodosItens.setOpaque(true);
        btnRemoverTodosItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRemoverTodosItensMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRemoverTodosItensMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRemoverTodosItensMouseExited(evt);
            }
        });
        painelLembretes.add(btnRemoverTodosItens, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 120, 20));

        btnRemoverItem.setBackground(new java.awt.Color(255, 255, 255));
        btnRemoverItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRemoverItem.setForeground(new java.awt.Color(255, 51, 51));
        btnRemoverItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRemoverItem.setText("REMOVER ITEM");
        btnRemoverItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        btnRemoverItem.setOpaque(true);
        btnRemoverItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRemoverItemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRemoverItemMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRemoverItemMousePressed(evt);
            }
        });
        painelLembretes.add(btnRemoverItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, 20));

        btnAdicionarItem.setBackground(new java.awt.Color(255, 255, 255));
        btnAdicionarItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAdicionarItem.setForeground(new java.awt.Color(255, 51, 51));
        btnAdicionarItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdicionarItem.setText("ADICIONAR ITEM");
        btnAdicionarItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        btnAdicionarItem.setOpaque(true);
        btnAdicionarItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdicionarItemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarItemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarItemMouseExited(evt);
            }
        });
        painelLembretes.add(btnAdicionarItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 120, 20));

        filtro.setBackground(new java.awt.Color(255, 255, 255));
        filtro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        filtro.setSelected(true);
        filtro.setText("FILTRAR POR TAG");
        filtro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroActionPerformed(evt);
            }
        });
        painelLembretes.add(filtro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 20));

        lblListaItens1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblListaItens1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblListaItens1.setText("LISTA DE ITENS");
        painelLembretes.add(lblListaItens1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 270, -1));

        tab.addTab("LEMBRETES", painelLembretes);

        painelTags.setBackground(new java.awt.Color(255, 255, 255));
        painelTags.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        painelTags.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tagAdd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tagAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        painelTags.add(tagAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 20));

        listaTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        listaTag.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        listaTag.setForeground(new java.awt.Color(255, 51, 51));
        listaTag.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaTag.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listaTag.setName(""); // NOI18N
        listaTag.setSelectionBackground(new java.awt.Color(255, 51, 51));
        listaTag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaTagMouseClicked(evt);
            }
        });
        scrollListaTag.setViewportView(listaTag);

        painelTags.add(scrollListaTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 240, 223));

        lblListaTagsCadastradas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblListaTagsCadastradas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblListaTagsCadastradas.setText("LISTA DE TAGS CADASTRADAS");
        painelTags.add(lblListaTagsCadastradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 240, -1));

        btnAdicionarTag.setBackground(new java.awt.Color(255, 255, 255));
        btnAdicionarTag.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAdicionarTag.setForeground(new java.awt.Color(255, 51, 51));
        btnAdicionarTag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdicionarTag.setText("ADICIONAR TAG");
        btnAdicionarTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        btnAdicionarTag.setOpaque(true);
        btnAdicionarTag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdicionarTagMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarTagMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarTagMouseExited(evt);
            }
        });
        painelTags.add(btnAdicionarTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 20));

        btnRemoverTodasTags.setBackground(new java.awt.Color(255, 255, 255));
        btnRemoverTodasTags.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRemoverTodasTags.setForeground(new java.awt.Color(255, 51, 51));
        btnRemoverTodasTags.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRemoverTodasTags.setText("REMOVER TUDO");
        btnRemoverTodasTags.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        btnRemoverTodasTags.setOpaque(true);
        btnRemoverTodasTags.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRemoverTodasTagsMouseClicked(evt);
            }
        });
        painelTags.add(btnRemoverTodasTags, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 20));

        btnRemoverTag.setBackground(new java.awt.Color(255, 255, 255));
        btnRemoverTag.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRemoverTag.setForeground(new java.awt.Color(255, 51, 51));
        btnRemoverTag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRemoverTag.setText("REMOVER TAG");
        btnRemoverTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        btnRemoverTag.setOpaque(true);
        btnRemoverTag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRemoverTagMousePressed(evt);
            }
        });
        painelTags.add(btnRemoverTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, 20));

        tab.addTab("TAGS", painelTags);

        javax.swing.GroupLayout PrincipalLayout = new javax.swing.GroupLayout(Principal);
        Principal.setLayout(PrincipalLayout);
        PrincipalLayout.setHorizontalGroup(
            PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PrincipalLayout.setVerticalGroup(
            PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        painelSuperior.setBackground(new java.awt.Color(255, 51, 51));
        painelSuperior.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                painelSuperiorMouseDragged(evt);
            }
        });
        painelSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                painelSuperiorMousePressed(evt);
            }
        });

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Cancel_20px.png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitMouseExited(evt);
            }
        });

        lblListaItens.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblListaItens.setForeground(new java.awt.Color(255, 255, 255));
        lblListaItens.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblListaItens.setText("INESQUECÍVEL");
        lblListaItens.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblListaItensMouseDragged(evt);
            }
        });
        lblListaItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblListaItensMousePressed(evt);
            }
        });

        javax.swing.GroupLayout painelSuperiorLayout = new javax.swing.GroupLayout(painelSuperior);
        painelSuperior.setLayout(painelSuperiorLayout);
        painelSuperiorLayout.setHorizontalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelSuperiorLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblListaItens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit)
                .addGap(5, 5, 5))
        );
        painelSuperiorLayout.setVerticalGroup(
            painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSuperiorLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(painelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblListaItens)
                    .addComponent(exit))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(painelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarTagMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarTagMouseEntered
        btnAdicionarTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
    }//GEN-LAST:event_btnAdicionarTagMouseEntered

    private void btnAdicionarTagMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarTagMouseExited
        btnAdicionarTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 1));
    }//GEN-LAST:event_btnAdicionarTagMouseExited

    private void btnRemoverItemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverItemMouseEntered
        btnRemoverItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
    }//GEN-LAST:event_btnRemoverItemMouseEntered

    private void btnRemoverItemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverItemMouseExited
        btnRemoverItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 1));
    }//GEN-LAST:event_btnRemoverItemMouseExited

    private void btnAdicionarItemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarItemMouseEntered
        btnAdicionarItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
    }//GEN-LAST:event_btnAdicionarItemMouseEntered

    private void btnAdicionarItemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarItemMouseExited
        btnAdicionarItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 1));
    }//GEN-LAST:event_btnAdicionarItemMouseExited

    private void btnAdicionarTagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarTagMouseClicked
        
        //verifica se foi adicionado o nome da tag no campo de texto antes de adicionar
        if (!"".equals(tagAdd.getText())) {
            escreverNoArquivo(tagAdd.getText(),1);
            tagAdd.setText("");
            lerOArquivo(1);
        }else{
            JOptionPane.showMessageDialog(null, "Por Favor Preencha o nome da TAG!");
        }
        
    }//GEN-LAST:event_btnAdicionarTagMouseClicked
    
    private void tagSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagSelectedActionPerformed
        
        try {
            //pega a tag atual
            tagAtual = tagSelected.getSelectedItem().toString();
            
            //se o filtro estiver marcado seleciona os itens apenas com a tag escolhida
            if(filtro.isSelected()){
               lerOArquivo(2); 
            }
            
            int a = tagSelected.getSelectedIndex();
            //verifica se a tag selecionada está vazia se estiver seleciona todos itens
            if (a == 0){
                lerOArquivo(4);
            }
            
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_tagSelectedActionPerformed

    private void btnAdicionarItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarItemMouseClicked
        
        //verifica se tem alguma tag escolhida
        if(tagAtual.equals("")){
            
            JOptionPane.showMessageDialog(null, "Por Favor escolha uma tag!");
            
        }else if(itemAdd.getText().equals("")){ //verifica se foi digitado o nome do item
            
            JOptionPane.showMessageDialog(null, "Por Favor preencha algum nome para o item!");
            
        }else{
            //se passou por todas as etapas adiciona os campos 
            
            escreverNoArquivo(itemAdd.getText(), 2);
            //se o filtro estiver marcado seleciona os itens apenas com a tag escolhida
            if(filtro.isSelected()){
               lerOArquivo(2); 
            }else{
                lerOArquivo(4);
            }
            lerOArquivo(3);//atualiza a lista temporaria
            itemAdd.setText("");
        }
        
    }//GEN-LAST:event_btnAdicionarItemMouseClicked
    
    private void listaItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaItemMouseClicked
    
        try {
            itemSelecionado = listaItem.getSelectedValue(); //seleciona o item clicado

            //SISTEMA DE DUPLO CLICK VERIFICANDO SE FOI CLICADO DUAS VEZES NA MESMA TUPLA.
            //CASO SEJA ELE ENVIA O CODIGO DA TUPLA PARA AREA DE TRANSFERENCIA
            contC++;
            if (contC == 1) {
                itemTEMP = listaItem.getSelectedValue();
            } else if (contC == 2) {
                if (itemTEMP.equals(itemSelecionado)) {
                    //adiciona na area de transferencia
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(itemSelecionado);
                    clipboard.setContents(selection, null);
                } else {
                    contC = 1;
                    itemTEMP = listaItem.getSelectedValue();
                }
            } else if (contC > 2) {
                contC = 1;
                itemTEMP = listaItem.getSelectedValue();

            }
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_listaItemMouseClicked

    private void btnRemoverItemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverItemMousePressed
        
        lerOArquivo(3);//atualiza a lista temporaria
        
        try {
            int tamanhoGeral = modelListaItemTEMP.getSize(); //pega o tamanho da lista com todos os itens
            if (tamanhoGeral == 1) {
                tamanhoGeral = 0; //se o tamanho da lista for 1 o indice deve ser 0
            }
            //percorre a lista toda
            for (int i = 0; i <= tamanhoGeral; i++) {
                
                //verifica se o item selecionado e igual ao item atual da lista completa
                if (itemSelecionado.equals(modelListaItemTEMP.getElementAt(i).toString())) {
                    modelListaItemTEMP.remove(i);//se for remove da lista completa
                    modelListaItem = (DefaultListModel) listaItem.getModel(); //limpa da lista visivel
                    modelListaItem.remove(listaItem.getSelectedIndex()); //remove
                    listaItem.setModel(modelListaItem);//seta novamente
                    break;
                }

            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Selecione o item que deseja remover");
        }

        listaItem.clearSelection(); //remove a marcação 
        int qtd = modelListaItemTEMP.getSize(); //pega o tamanho da lista ja com item removido
        
        //ATUALIZA O ARQUIVO TXT
        File arquivo = new File("item.txt");
        
        try {
            FileWriter escrita = new FileWriter(arquivo);
            PrintWriter saida = new PrintWriter(escrita);
            //saida.write(msg);
            for (int i = 0; i < qtd; i++) {
                
                if (i == 0) {  //verifica se e o primeiro indice para não quebrar linha
                    saida.append(modelListaItemTEMP.getElementAt(i).toString());
                } else {
                    saida.append(Constants.NEWLINE + modelListaItemTEMP.getElementAt(i).toString());
                }

            }
            saida.close();
            escrita.close();
        } catch (IOException ex) {

        }
    }//GEN-LAST:event_btnRemoverItemMousePressed

    private void filtroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroActionPerformed
        
        //verifica se o filtro está selecionado caso esteja ele atualiza a lista de itens de a cordo com a tag 
        if(filtro.isSelected()){
            if(tagSelected.getSelectedIndex() == 0){
                lerOArquivo(4);
            }else{
                lerOArquivo(2);   
            }
        }else{
            lerOArquivo(4); //se n estiver selecionado ele atualiza a lista de itens com todos os itens
        }
    }//GEN-LAST:event_filtroActionPerformed
    
    private void listaTagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaTagMouseClicked
        tagSelecionada = listaTag.getSelectedValue(); //seta a tag selecionada
    }//GEN-LAST:event_listaTagMouseClicked

    private void btnRemoverTagMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverTagMousePressed
        
        lerOArquivo(5);
        try {
            int tamanhoGeral = modelListaTagTEMP.getSize(); //pega o tamanho da lista de tags
            if (tamanhoGeral == 1) {
                tamanhoGeral = 0;//verifica se e = a 1 se for o indice deve ser 0
            }

            for (int i = 0; i <= tamanhoGeral; i++) { //percorre a lista toda

                //verifica se a tag escolhida para excluir pertence a lista de tag completa
                
                if (tagSelecionada.equals(modelListaTagTEMP.getElementAt(i).toString())) {
                    modelListaTagTEMP.remove(i); //remove
                    modelListaTag = (DefaultListModel) listaTag.getModel();//remove da lista visivel
                    modelListaTag.remove(listaTag.getSelectedIndex());//remove o item da lista visivel
                    listaTag.setModel(modelListaTag);//atualiza a lista visivel
                    break;
                }

            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Selecione a Tag que deseja remover");
        }

        listaTag.clearSelection();//remove a seleçao da tag
        int qtd = modelListaTagTEMP.getSize();//pega a quantida de item apos remoção
        
        File arquivo = new File("tag.txt");
        try {
            FileWriter escrita = new FileWriter(arquivo);
            PrintWriter saida = new PrintWriter(escrita);
            
            for (int i = 0; i < qtd; i++) {//percorre a lista e atualiza o arquivo sem o item removido
                if (i == 0) {
                    saida.append(modelListaTagTEMP.getElementAt(i).toString());
                } else {
                    saida.append(Constants.NEWLINE + modelListaTagTEMP.getElementAt(i).toString());
                }

            }
            saida.close();
            escrita.close();
        } catch (IOException ex) {

        }
    }//GEN-LAST:event_btnRemoverTagMousePressed

    private void exitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseEntered
        exit.setIcon(exitHover.getIcon());
    }//GEN-LAST:event_exitMouseEntered

    private void exitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseExited
        exit.setIcon(exitDefault.getIcon());
    }//GEN-LAST:event_exitMouseExited

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitMouseClicked

    private void painelSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelSuperiorMousePressed
       posFrameX = evt.getX();
       posFrameY = evt.getY();
    }//GEN-LAST:event_painelSuperiorMousePressed

    private void painelSuperiorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelSuperiorMouseDragged
        this.setLocation(this.getLocation().x + evt.getX()- posFrameX,this.getLocation().y + evt.getY() - posFrameY);
    }//GEN-LAST:event_painelSuperiorMouseDragged

    private void lblListaItensMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblListaItensMousePressed
       posFrameX = evt.getX();
       posFrameY = evt.getY();
    }//GEN-LAST:event_lblListaItensMousePressed

    private void lblListaItensMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblListaItensMouseDragged
        this.setLocation(this.getLocation().x + evt.getX()- posFrameX,this.getLocation().y + evt.getY() - posFrameY);
    }//GEN-LAST:event_lblListaItensMouseDragged

    private void btnRemoverTodosItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverTodosItensMouseClicked
        int r = JOptionPane.showConfirmDialog(null, "TEM CERTEZA QUE DESEJA EXCLUIR TODOS OS ITENS ?");
        if(r == JOptionPane.YES_OPTION){
            limparArquivo(1);
            modelListaItem = new DefaultListModel();
            modelListaItemTEMP = new DefaultListModel();
            listaItem.setModel(modelListaItem);
        }
        
    }//GEN-LAST:event_btnRemoverTodosItensMouseClicked

    private void btnRemoverTodosItensMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverTodosItensMouseEntered
        btnRemoverTodosItens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
    }//GEN-LAST:event_btnRemoverTodosItensMouseEntered

    private void btnRemoverTodosItensMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverTodosItensMouseExited
        btnRemoverTodosItens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 1));
    }//GEN-LAST:event_btnRemoverTodosItensMouseExited

    private void btnRemoverTodasTagsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemoverTodasTagsMouseClicked
        int r = JOptionPane.showConfirmDialog(null, "TEM CERTEZA QUE DESEJA EXCLUIR TODAS AS TAGS ?");
        if(r == JOptionPane.YES_OPTION){
            limparArquivo(2);
            modelListaTag = new DefaultListModel();
            modelListaTagTEMP = new DefaultListModel();
            listaTag.setModel(modelListaTag);  
        }

    }//GEN-LAST:event_btnRemoverTodasTagsMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Principal;
    private javax.swing.JLabel btnAdicionarItem;
    private javax.swing.JLabel btnAdicionarTag;
    private javax.swing.JLabel btnRemoverItem;
    private javax.swing.JLabel btnRemoverTag;
    private javax.swing.JLabel btnRemoverTodasTags;
    private javax.swing.JLabel btnRemoverTodosItens;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel exitDefault;
    private javax.swing.JLabel exitHover;
    private javax.swing.JCheckBox filtro;
    private javax.swing.JTextArea itemAdd;
    private javax.swing.JLabel lblListaItens;
    private javax.swing.JLabel lblListaItens1;
    private javax.swing.JLabel lblListaTagsCadastradas;
    private javax.swing.JLabel lblSelecioneATag;
    private javax.swing.JList<String> listaItem;
    private javax.swing.JList<String> listaTag;
    private javax.swing.JPanel painelLembretes;
    private javax.swing.JPanel painelSuperior;
    private javax.swing.JPanel painelTags;
    private javax.swing.JScrollPane scrolListItem;
    private javax.swing.JScrollPane scrollItemAdd;
    private javax.swing.JScrollPane scrollListaTag;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextField tagAdd;
    private javax.swing.JComboBox<String> tagSelected;
    // End of variables declaration//GEN-END:variables
}
