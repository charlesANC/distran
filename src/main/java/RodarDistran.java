import java.security.InvalidParameterException;

public class RodarDistran {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Selecione o método de seleção de transcodificadores:");
			System.out.println("    1: Random choice ");
			System.out.println("    2: MAB UCB ");
			System.out.println("    3: ReNoS ");			
			System.out.println("    4: ReNoS com FIRE alterado ");
			System.out.println("    5: Random choice - Sem white washing ");			
			System.out.println("    6: MAB UCB - Sem white washing ");
			System.out.println("    7: ReNoS - Sem white washing  ");			
			System.out.println("    8: ReNoS com FIRE alterado - Sem white washing ");
			System.out.println("    9: ReNoS 2 com FIRE");
			System.out.println("   10: ReNoS 2 com FIRE - Sem white washing ");			
		}
		
		try {
			jade.Boot.main(selecionaConfiguracao(args[0]));		
		} catch (InvalidParameterException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public static String[] selecionaConfiguracao(String selecao) {
		if ("1".contentEquals(selecao)) {
			System.out.println("Selecionando Random Choice...");
			return configuracaoRandom();
		} else if ("2".contentEquals(selecao)) {
			System.out.println("Selecionando MAB UCB...");
			return configuracaoUCB();
		} else if ("3".contentEquals(selecao)) {
			System.out.println("Selecionando ReNoS...");
			return configuracaoReNoS();
		} else if ("4".contentEquals(selecao)) {
			System.out.println("Selecionando ReNoS modificado...");
			return configuracaoReNoSAlterado();
		} else if ("5".contentEquals(selecao)) {
			System.out.println("Selecionando Random Choice Sem White Washing...");
			return configuracaoUCBSemWhiteWashing();
		} else if ("6".contentEquals(selecao)) {
				System.out.println("Selecionando MAB UCB sem White Washing...");
				return configuracaoUCBSemWhiteWashing();			
		} else if ("7".contentEquals(selecao)) {
			System.out.println("Selecionando ReNoS sem White Washing...");
			return configuracaoReNoSSemWhiteWashing();			
		} else if ("8".contentEquals(selecao)) {
			System.out.println("Selecionando ReNoS modificado sem White Washing...");
			return configuracaoReNoSAlteradoSemWhiteWashing();			
		} else if ("9".contentEquals(selecao)) {
			System.out.println("Selecionando ReNoS modificado sem White Washing...");
			return configuracaoReNoS2();
		} else if ("10".contentEquals(selecao)) {
			System.out.println("Selecionando ReNoS modificado sem White Washing...");
			return configuracaoReNoS2SemWhiteWashing();						
		} else {
			throw new InvalidParameterException("Configuração " + selecao + " não reconhecida!");
		}
	}
	
	public static String[] configuracaoRandom() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"br1:br.unb.cic.comnet.distran.agents.broker.randomChoice.RandomChoiceBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"		
			};		
	}	
	
	public static String[] configuracaoRandomSemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"br2:br.unb.cic.comnet.distran.agents.broker.randomChoice.RandomChoiceBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)			
			};		
	}
	
	public static String[] configuracaoUCB() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"bu1:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"				
		};
	}	
	
	public static String[] configuracaoUCBSemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"bu2:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)					
		};
	}		
	
	public static String[] configuracaoReNoS() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"br1:br.unb.cic.comnet.distran.agents.broker.FIRE.FIRETRMBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"								
		};
	}
	
	public static String[] configuracaoReNoSSemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"br2:br.unb.cic.comnet.distran.agents.broker.FIRE.FIRETRMBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)										
		};
	}	
	
	public static String[] configuracaoReNoS2() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"br1:br.unb.cic.comnet.distran.agents.broker.mab.MabReNosTwoBrokerFIRE"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"								
		};
	}
	
	public static String[] configuracaoReNoS2SemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"br2:br.unb.cic.comnet.distran.agents.broker.mab.MabReNosTwoBrokerFIRE"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)										
		};
	}	
	
	public static String[] configuracaoReNoSAlterado() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"bra1:br.unb.cic.comnet.distran.agents.broker.FIRE.ModifiedFIRETRMBroker" 
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"					
		};
	}	
	
	public static String[] configuracaoReNoSAlteradoSemWhiteWashing() {
		return new String[] {
			"-gui", 
			" -agents " + 
					"bra2:br.unb.cic.comnet.distran.agents.broker.FIRE.ModifiedFIRETRMBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)			
		};
	}	
	
	private static String montaRandomTranscoders(String profiles) {
		return montaProfiles(profiles, "t", "br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder");
	}		
	
	private static String montaSequentialPlayers(int count) {
		return montaStringAgente("v", "br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer", count);
	}	
	
	private static String montaProfiles(String profiles, String prefixo, String classe) {
		StringBuilder resultado = new StringBuilder();
		
		int count = 1;
		for(char a: profiles.toCharArray()) {
			StringBuilder builder = new StringBuilder();
			builder.append(";");
			builder.append(prefixo);
			builder.append(Character.toLowerCase(a));
			builder.append(count++);
			builder.append(":");
			builder.append(classe);
			builder.append("(");
			builder.append(a);
			builder.append(")");
			
			resultado.append(builder.toString());
		}
		
		return resultado.toString();
	}

	private static String montaStringAgente(String prefixo, String classe, int count) {
		StringBuilder resultado = new StringBuilder();
		
		for(int i = 1; i <= count; i++) {
			StringBuilder builder = new StringBuilder();
			
			builder.append(";");			
			builder.append(prefixo);
			builder.append(i);
			builder.append(":");
			builder.append(classe);
			
			resultado.append(builder.toString());
		}
		
		return resultado.toString();		
	}	
	
	/*
	public static void main(String[] args) {
		String[] rodarDistran = new String[] {
				"-gui", 
				" -agents " + 
					"b1:br.unb.cic.comnet.distran.agents.broker.SequentialBroker(R);" + 
					"ta1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(A);" +
					"tb1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" +					
					"tb2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" +
					"tc1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" +					
					"tc2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" +
					"v1:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v2:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v3:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v4:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v5:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v6:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v7:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v8:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v9:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v10:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v11:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +
					"v12:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v13:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v14:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v15:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v16:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v17:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v18:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v19:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v20:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v21:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v22:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +
					"v23:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +					
					"v24:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer"};
		
		jade.Boot.main(rodarDistran);
	}	
*/	
	

}
