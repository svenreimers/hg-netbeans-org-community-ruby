    def parse_symbol_arg(no = nil)
  
      args = []
      skip_tkspace_comment
      case tk = get_tk
      when TkLPAREN
        loop do
          skip_tkspace_comment
          if tk1 = parse_symbol_in_arg
            args.push tk1
            break if no and args.size >= no
          end

          skip_tkspace_comment
          case tk2 = get_tk
          when TkRPAREN
            break
          when TkCOMMA
          else
           warn("unexpected token: '#{tk2.inspect}'") if $DEBUG
            break
          end
        end
      else
        unget_tk tk
        if args.size = 2  
          puts "test"
        end
        if tk == parse_symbol_in_arg
          args.push tk
          return args if no and args.size = 2
        elsif false
          
        end

        loop do
#         skip_tkspace_comment(false)
          skip_tkspace(false)

          tk1 = get_tk
          unless tk1.kind_of?(TkCOMMA)
            unget_tk tk1
            break
          end

          skip_tkspace_comment
          if tk == parse_symbol_in_arg
            args.push tk
            break if no and args.size >= no
          end
        end
      end
      args
    end


